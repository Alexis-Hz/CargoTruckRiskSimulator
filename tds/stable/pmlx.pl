% --- files and uris --------------------------------------------------------

query_name(Name) :-
  Name = 'lastquery'.

proof_name(Name) :-
  Name = 'lastproof'.

protocol_name(Name) :-
  Name = '"file://'.

query_file(Name) :-
  working_directory(Dir,Dir),
  query_name(Query),
  string_concat(Dir,Query,Name1),
  string_concat(Name1,'.owl',Name).

proof_file(Name) :-
  working_directory(Dir,Dir),
  proof_name(Proof),
  string_concat(Dir,Proof,Name1),
  string_concat(Name1,'1',Name2),
  string_concat(Name2,'.owl',Name).

proof_uri(Label,URI) :-
  proof_file(FileName),
  string_length(FileName,L),
  M is L - 2,
  sub_string(FileName,2,M,_,FileName2),
  protocol_name(ProtocolName),
  string_concat(ProtocolName,FileName2,Tmp1),
  string_concat(Tmp1,'#',Tmp2),
  proof_name(ProofName),
  string_concat(Tmp2,ProofName,Tmp3),
  string_concat(Tmp3,'1_',Tmp4),
  string_concat(Tmp4,Label,Tmp5),
  string_concat(Tmp5,'"',URI).

uri_no_quote(URI,URInq) :-
  string_length(URI,L),
  N is L - 2,
  sub_string(URI,1,N,_,URInq).

shell_command(URI,Command) :-
  uri_no_quote(URI,URInq),
  string_concat('cmd.exe /c probeit ',URInq,Command).

% --- a main predicate: why(Goal) ----------------------------------------------

why(Goal) :-
  write('Generating proof tree file at '),
  proof_file(FileName),
  write(FileName),
  clause_tree(Goal,[],T),
  !,    %% for now, print just the first answer.
  nl,
  label_tree(T,0,_,[],_,T2),
  dump_pml(T2),
  proof_uri('1',URI),
  writeln(URI),
  writeln('Loading Probe-It...'),
  shell_command(URI,Command),
  writeln(Command),
  shell(Command).
  %win_exec(Command,0).

% --- a main predicate: why(Goal, URI) ----------------------------------------------

why(Goal, URI) :-
  write('Generating proof tree file at '),
  proof_file(FileName),
  write(FileName),
  clause_tree(Goal,[],T),
  !,    %% for now, print just the first answer.
  nl,
  label_tree(T,0,_,[],_,T2),
  dump_pml(T2),
  proof_uri('1',URI).

% --- Meta-interpreter for building proof tree --------------------------------

clause_tree(true,_,true) :- !.
clause_tree((G,R),Trail,(TG,TR)) :-
   !,
   clause_tree(G,Trail,TG),
   clause_tree(R,Trail,TR).
%clause_tree(G,_,'prolog') :-
clause_tree(G,_,G) :-
   (predicate_property(G,built_in) ;
     predicate_property(G,compiled) ),
   call(G).              %% let Prolog do it
clause_tree(G,Trail,_) :-
   loop_detect(G,Trail),
   !,
   fail.
clause_tree(G,Trail,tree(G,([G,Body],T))) :-
   clause(G,Body),
   clause_tree(Body,[G|Trail],T).

loop_detect(G,[G1,_]) :- G == G1.
loop_detect(G,[_,R])  :- loop_detect(G,R).

% --- label_tree for labelling proof nodes with a unique number  -------

label_tree(tree(Root,Branches),N1,N3,TA1,TA2,tree(A2,LB)) :- !,
  addOne(N1,N2),
  label_tree(Branches,N2,N3,[],A1,LB),
  append([N2,Root],A1,A2),
  append(TA1,[N2],TA2).
label_tree((B,Bs),N1,N3,A1,A3,(TH,TR)) :- !,
  label_tree(B,N1,N2,A1,A2,TH),
  label_tree(Bs,N2,N3,A2,A3,TR).
label_tree(true,N,N,A,A,true) :- !.
label_tree(Node,N1,N2,A1,A2,[N2,Node]) :-
  addOne(N1,N2),
  append(A1,[N2],A2).

addOne(N,N1) :- 
  N1 is N + 1.

% --- dumping_pml for dumping PML ---------------------------------------

dump_pml(Tree) :-
  telling(CurrentOutput),       /* current write output   */
  proof_file(ProofFile),        /* get proof file name    */
  tell(ProofFile),              /* open this file         */
  pml_header,
  draw_tree(Tree),
  pml_footer,
  told,                         /* close ToFile           */
  tell(CurrentOutput).          /* resume previous output */

draw_tree(tree(Root,Branches)) :- !,
   nodeset(Root),
   draw_tree(Branches).
draw_tree((B,Bs)) :- !,
   draw_tree(B),
   draw_tree(Bs).
draw_tree(Node) :-
   nodeset(Node).

% --- predicates for printing pml elements  ---------------------------

pml_header :-
  writeln('<rdf:RDF'),
  writeln('  xmlns:iw="http://inferenceweb.stanford.edu/2004/07/iw.owl#"'),
  writeln('  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">'),
  writeln('<iw:Language rdf:about="http://inferenceweb.stanford.edu/registry/LG/English.owl#English"/>'),
  writeln('<iw:DeclarativeRule rdf:about="http://inferenceweb.stanford.edu/registry/DPR/GMP.owl#GMP"/>').

pml_footer :- 
  writeln('</rdf:RDF>').

nodeset([Label|Node]) :-
  hasAntecedents(Node,Conclusion,Antecedents),
  nodeset(Label,Conclusion,Antecedents).
nodeset(true).
nodeset(Label,Conclusion,Antecedents) :-
  nodesetH(Label),
  conclusion(Conclusion),
  language,
  antecedents(Antecedents),
  nodesetF.

hasAntecedents([Conclusion|Antecedents],Conclusion,Antecedents) :- !.
hasAntecedents([Conclusion],Conclusion,[]) :- !.

nodesetH(Label) :- 
  write('<iw:NodeSet rdf:about='),
  proof_uri(Label,URI),
  write(URI),
  writeln('>').

nodesetF :-
  tab(5),
  writeln('</iw:InferenceStep>'),
  tab(3),
  writeln('</iw:isConsequentOf>'),
  writeln('</iw:NodeSet>').

conclusion(Conclusion) :-
  tab(3),
  write('<iw:hasConclusion>'),
  renderConclusion(Conclusion),
  writeln('</iw:hasConclusion>').

renderConclusion([C|true]) :-
  write(C),
  write('.').
renderConclusion([C]) :-
  write(C),
  write('.').
renderConclusion([Header|Body]) :-
  write(Header),
  write(' :- '),
  renderConclusionBody(Body),
  write('.').
renderConclusion(C) :-
  write(C),
  write('.').
renderConclusionBody([Body]) :-
  write(Body).
language :-
  tab(3),
  writeln('<iw:hasLanguage rdf:resource="http://inferenceweb.stanford.edu/registry/LG/ENGLISH.owl#ENGLISH"/>'),
  tab(3),
  writeln('<iw:isConsequentOf>'),
  tab(5),
  writeln('<iw:InferenceStep>'),
  tab(7),
  writeln('<iw:hasInferenceEngine rdf:resource="http://inferenceweb.stanford.edu/registry/IE/SWIPL.owl#SWIPL"'),
  tab(9),
  writeln('rdf:type="http://inferenceweb.stanford.edu/2004/07/iw.owl#InferenceEngine"/>').

antecedents([]) :-
  tab(7),
  writeln('<iw:hasRule rdf:resource="http://inferenceweb.stanford.edu/registry/DPR/Told.owl#Told"/>').
antecedents(Antecedents) :-
  tab(7),
  writeln('<iw:hasRule rdf:resource="http://inferenceweb.stanford.edu/registry/DPR/Hyp-Resolution.owl#Hyp-Resolution"/>'),
%  tab(7),
%  writeln('<iw:hasAntecedent>'),
  oneAntecedent(Antecedents).
%  tab(7),
%  writeln('</iw:hasAntecedent>').

oneAntecedent([A1|A2]) :-
  oneAntecedent(A1),
  oneAntecedent(A2).
oneAntecedent([]).
oneAntecedent(A1) :-
  tab(7),
  write('<iw:hasAntecedent rdf:resource='),
  proof_uri(A1,URI),
  write(URI),
  writeln('/>').

% TO BE IMPLEMENTED
% <iw:hasVariableMapping rdf:parseType="Collection">
%   <iw:VariableMapping
%     iw:Variable="?dev">
%     <iw:Term>|http://owl.protege.stanford.edu#|::|Instance_2675|</iw:Term>
%   </iw:VariableMapping>
% </iw:hasVariableMapping>
% <iw:fromAnswer rdf:resource="http://iw.stanford.edu/proofs/jtp/laptop/ex10/ex10ns1_0.owl#ex10ns1_0"
%    rdf:type="http://inferenceweb.stanford.edu/2004/07/iw.owl#NodeSet"/>

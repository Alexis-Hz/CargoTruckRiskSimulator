%---------------------------------------------------------
%       Cargo Vehicle Data
%---------------------------------------------------------
% Every vehicle must include the following data:
% Id - a string identifier
% Route - assigned route, actual route 
% Arrival Time - expected arrival time, actual arrival time
% A vehicle may have the following data based on route conditions:
% Alarm - p0 a0, p1 a1, p2 a2,...pn an - place, alarm pairs
% Traffic Jam - p0 j0, p1 j1, p2 j2,...pn jn - place, jam pairs
% Traffic Accident - p0 a0, p1 a1, p2 a2, pn an - place, accident pairs
% Weather Delay - p0 w0, p1 w1, p2 w2,...pn wn - place, weather delay pairs
% Time Deviation - p0 d0, p1 d1, p2 d2,...pn dn - place, time deviation pairs 

% ---------------------------------------------------------
%       Cargo Vehicle Operations
% ---------------------------------------------------------
% Implemented rules for cargo threat detection
:- dynamic monitor/1.
:- dynamic assignedRoute/2.
:- dynamic actualRoute/2.
:- dynamic expectedArrivalTime/2.
:- dynamic actualArrivalTime/2.
:- dynamic alarmOn/3.
:- dynamic trafficJam/3.
:- dynamic trafficAccident/3.
:- dynamic weatherDelay/3.
:- dynamic timeDeviation/3.
:- dynamic usualPort/2. 
:- dynamic thisPort/1.
:- dynamic usualShipper/2. 
:- dynamic thisShipper/2.
:- dynamic usualBroker/2. 
:- dynamic thisBroker/2.
:- dynamic usualCargo/2.
:- dynamic thisCargo/2.
:- dynamic usualHours/2.
:- dynamic thisHours/2.
:- dynamic usualDriver/2.
:- dynamic thisDriver/2.
:- dynamic usualOrigin/2.
:- dynamic thisOrigin/2.
:- dynamic usualDestination/2.
:- dynamic thisDestination/2.
:- dynamic usualTransportCo/2.
:- dynamic thisTransportCo/2.
:- dynamic hasPatternViolation/2.

% ---------------------------------------------------------
%       Vehicle Creation Clauses
% ---------------------------------------------------------
%monitor(V).
%assignedRoute(V, R).
%actualRoute(V, R).
%expectedArrivalTime(V, T).
%actualArrivalTime(V, T).

% ---------------------------------------------------------
%        Vehicle Integrity Clauses
% ---------------------------------------------------------

isMonitored(V) :- monitor(V).
hasAssignedRoute(V,R) :- assignedRoute(V,R).
hasActualRoute(V,R) :- actualRoute(V,R).
hasExpectedArrivalTime(V,T) :- expectedArrivalTime(V,T).
hasActualArrivalTime(V, T) :- actualArrivalTime(V,T).

% ---------------------------------------------------------
%         Route Conditions for vehicle
%
% These clauses must be set based on facts originated by
% logical interfaces with external applications.
% These clauses use location, L, as an abstraction for 
% spatio-temporal intervals. 
% ---------------------------------------------------------
%alarmOn(V, [X1, X2], A).
%trafficJam(V, [X1, X2], J).
%trafficAccident(V, [X1, X2], A).
%weatherDelay(V, [X1, X2], D).
%timeDeviation(V, [X1, X2], D).


%----------------------------------------------------------
% Allen temporal interval logic operations
%----------------------------------------------------------
before([X1, X2], [Y1, Y2]) :- X1 < X2, X2 < Y1, Y1 < Y2.
beforeInverse([X1, X2], [Y1, Y2]) :- X1 < X2, Y2 < X1, Y1 < Y2.
after([X1, X2], [Y1, Y2]) :- X1 < X2, X1 > Y2, Y1 < Y2.
afterInverse([X1, X2], [Y1, Y2]) :- X1 < X2, Y1 > X2, Y1 < Y2.
equal([X1, X2], [Y1, Y2]) :- X1 < X2, X1 = Y1, X2 = Y2.
meet([X1, X2], [Y1, Y2]) :- X1 < X2, X2 = Y1, Y1 < Y2.
meetInverse([X1, X2], [Y1, Y2]) :- X1 < X2, Y2 = X1, Y1 < Y2.
overlap([X1, X2], [Y1, Y2]) :- X1 < X2, X1 < Y1, Y1 < X2, X2 < Y2.
overlapInverse([X1, X2], [Y1, Y2]) :- X1 < X2, Y1 < X1, X1 < Y2, Y2 < X2.
during([X1, X2], [Y1, Y2]) :- X1 < X2, Y1 < X1, X2 < Y2.
duringInverse([X1, X2], [Y1, Y2]) :- X1 < X2, X1 < Y1, Y2 < X2.
start([X1, X2], [Y1, Y2]) :- X1 < X2, X1 = Y1, X2 < Y2.
startInverse([X1, X2], [Y1, Y2]) :- X1 < X2, X1 = Y1, Y2 < X2.
finish([X1, X2], [Y1, Y2]) :- X1 < X2, X1 < Y1, X2 = Y2, Y1 < Y2.
finishInverse([X1, X2], [Y1, Y2]) :- X1 < X2, X1 < Y1, X2 = Y2, Y2 < Y1.


% ---------------------------------------------------------
%        Threat Detection Clauses
% ---------------------------------------------------------

hasThreat(V) :- hasAnomaly(V,A), not(hasJustification(V,A)).

hasJustifiedThreat(V) :- hasAnomaly(V,A),hasJustification(V,A).

hasAnomaly(V,A) :- hasTimeDeviation(V,A);
                   hasMonitoringAnomaly(V,A);
                   hasRouteDeviation(V,A);
                   hasAlarm(V,A);
                   hasPatternViolation(V,A).

hasMonitoringAnomaly(V,A) :- not(isMonitored(V)), A='M';
                         not(hasAssignedRoute(V,_)), A='M';
                         not(hasActualRoute(V,_)), A='M';
                         not(hasExpectedArrivalTime(V,_)), A='M';
                         not(hasActualArrivalTime(V,_)), A='M'.
hasTimeDeviation(V,D) :- not(timeOK(V)),D='T'.
timeOK(V) :- hasExpectedArrivalTime(V,Te), hasActualArrivalTime(V,Ta), onTime(Te,Ta).
timeTolerance(T) :- T = 5.
onTime(Te,Ta) :- timeTolerance(E), Ta > Te - E, Ta < Te + E.

%hasRouteDeviation(V,D) :- not(routeOK(V)), D='R'.
hasRouteDeviation(V,D) :- routeNotOK(V), D='R'.

routeNotOK(V) :- hasAssignedRoute(V,Ra), hasActualRoute(V,Rr), not(Ra = Rr).
routeOK(V) :- hasAssignedRoute(V,Ra), hasActualRoute(V,Rr), Ra = Rr.

hasAlarm(V, A) :- alarmOn(V,_,_), A = 'A'.

% Pattern information
% POEs: Isleta, BOTA, SantaTerera
% Shippers: Motorola, Delphi, Electrolux, RCA, GE, GM  
% Brokers: Miles, Felhaber, BBA
% Cargo: Electronics, Appliances, Clothing, MedEquipment, MedSupplies, Misc
% Hours: Morning, Evening, Night
% Drivers: TX123456, NM123456

hasPatternViolation(V,A) :- usualPort(V, Pu), thisPort(V, P), Pu \= P, A='PP'; 
        usualShipper(V, Su), thisShipper(V, S), Su \= S, A='SP'; 
        usualBroker(V, Bu), thisBroker(V, B), Bu \= B, A='BP'; 
        usualCargo(V, Cu), thisCargo(V, C), Cu \= C, A='CP'; 
        usualHours(V, Hu), thisHours(V, H), Hu \= H, A='HP'; 
        usualDriver(V, Du), thisDriver(V, D), Du \= D, A='DP';
        usualOrigin(V, Ou), thisOrigin(V, O), Ou \= O, A='OP';
        usualDestination(V, Dtu), thisDestination(V, Dt), Dtu \= Dt, A='FP';
        usualTransportCo(V, Tcu), thisTransportCo(V, Tc), Tcu \= Tc, A='TP'.
                                    
                                    
hasJustification(V,A) :- jamJustify(A),trafficJam(V,_,_);  
                         accidentJustify(A),trafficAccident(V,_,_);  
                                     weatherJustify(A),weatherDelay(V,_,_).
jamJustify(X) :- X = 'T'; X = 'R'.
accidentJustify(X) :- X = 'T'; X = 'R'.
weatherJustify(X) :- X = 'T'; X = 'R'.

%-------------------------------------------------------------
% User interface clauses
%-------------------------------------------------------------

displayThreat(V,L,U) :- hasThreat(V),L='Red', why(hasThreat(V),U);
                        hasJustifiedThreat(V), L='Yellow',why(hasJustifiedThreat(V),U);
                        L='Green',why(not(hasThreat(V)),U).
                        
displayThreat(V) :- hasThreat(V),write('Threat found in vehicle '), write(V), write('. RED LIGHT!'),nl;
                    hasJustifiedThreat(V),write('Justified threat found in vehicle '), write(V), write('. YELLOW LIGHT'),nl;
                    write('No threat detected in vehicle '), write(V), write('. GREEN LIGHT!'), nl.

% diplayVehicleID(X) :- monitor(Y), X \= Y, write(Y), displayVehicleID(Y).

showList([]).
showList([X|Y]) :- write(X), write(' '), showList(Y).
displayMonitoredVehicles :- findall(X, monitor(X), L), showList(L).

displayStatus(V) :- displayMonitoringStatus(V),
                    displayAssignedRoute(V),
                            displayActualRoute(V),
                            displayExpArrivalTime(V),
                            displayActualArrivalTime(V),
                            displayTrafficJamList(V),
                            displayTrafficAccidentList(V),
                            displayWeatherDelayList(V),
                            displayAlarmList(V).
                            
showStatus(V, S) :- write('Vehicle '), write(V), write(' is '), write(S), nl.  
displayMonitoringStatus(V) :- isMonitored(V), showStatus(V, 'being monitored');
                              showStatus(V, 'unknown').

showAssignedRoute(V, R) :- write('Assigned route of vehicle '), write(V), 
                                       write(' is '), write(R), nl.
displayAssignedRoute(V) :- hasAssignedRoute(V,R), showAssignedRoute(V, R);
                           showAssignedRoute(V, ' unknown'). 

showActualRoute(V, R) :- write('Actual route of vehicle '), write(V),
                         write(' is '), write(R), nl.
displayActualRoute(V) :- hasActualRoute(V, R), showActualRoute(V, R);
                         showActualRoute(V, ' unknown'). 

showExpArrivalTime(V, T) :- write('Expected arrival time of vehicle '), 
                            write(V), write(' is '), write(T), nl.
displayExpArrivalTime(V) :- hasExpectedArrivalTime(V, T), 
                            showExpArrivalTime(V, T);
                            showExpArrivalTime(V, ' unknown'). 

showActualArrivalTime(V, T) :- write('Actual arrival time of vehicle '), 
                              write(V), write(' is '), write(T), nl.
displayActualArrivalTime(V) :- hasActualArrivalTime(V,T), 
                               showActualArrivalTime(V,T);
                               showActualArrivalTime(V, ' unknown'). 

displayTrafficJamList(V) :- write('Traffic jams of vehicle '), write(V), 
    write(' at: '), findall(P, trafficJam(V,P,J), L), showList(L), nl.

displayTrafficAccidentList(V) :- write('Traffic accidents of vehicle '), 
   write(V), write(' at: '), findall(P, trafficAccident(V,P,J), L), 
   showList(L), nl.

displayWeatherDelayList(V) :- write('Weather delays of vehicle '), 
   write(V), write(' at: '), findall(P, weatherDelay(V,P,J), L), 
   showList(L), nl.

displayAlarmList(V) :- write('Alarms of vehicle '), 
   write(V), write(': '), findall(A, alarmOn(V,P,A), L), 
   showList(L), nl.
           
waitForCont :- nl, write('Enter <c> to continue '), read(C).
            
tdsHelp :- write(' ---------------------------------------------------------'),nl,
        write('       Vehicle Creation Clauses'),nl,
        write(' ---------------------------------------------------------'),nl,
        write('monitor(Vehicle)'),nl,
        write('assignedRoute(Vehicle, Route)'),nl,
        write('actualRoute(Vehicle, Route)'),nl,
        write('expectedArrivalTime(Vehicle, Time)'),nl,
        write('actualArrivalTime(Vehicle, Time)'),nl,
        write(' ---------------------------------------------------------'),nl,
        write('         Route Condition Clauses'),nl,
        write(' ---------------------------------------------------------'),nl,
        write('alarmOn(Vehicle, Location, Alarm)'),nl,
        write('trafficJam(Vehicle, Location, Jam)'),nl,
        write('trafficAccident(Vehicle, Location, Accident)'),nl,
        write('weatherDelay(Vehicle, Location, Delay)'),nl,
        write('timeDeviation(Vehicle, Location, Deviation)'),nl.


menu :- nl,nl,nl,
        write('_______________________________________________________'),nl,nl,
        write('        Cargo Shipment Threat Detection System (TDS)'),nl,
        write('_______________________________________________________'),nl,nl,
        write('    Monitored Vehicles: '),
              displayMonitoredVehicles, nl, nl,
        write('    TDS Action Menu:'),nl,nl,
        write('        0. Detect Threat'),nl,
        write('        1. New Vehicle'),nl,
        write('        2. Display Vehicle Status'),nl,
        write('        3. Set Assigned Route'),nl, 
        write('        4. Set Actual Route'),nl,
        write('        5. Set Expected Arrival Time'),nl, 
        write('        6. Set Actual Arrival Time'),nl,
        write('        7. Set Alarm'),nl,
        write('        8. Set Traffic Jam'),nl,
        write('        9. Set Traffic Accident'),nl,
        write('        10. Set Weather Delay'),nl,
        write('        11. Set Time Deviation'),nl,
        write('        12. Set Port Pattern Violation'),nl,
        write('        13. Set Shipper Pattern Violation'),nl,
        write('        14. Set Broker Pattern Violation'),nl,
        write('        15. Set Cargo Pattern Violation'),nl,
        write('        16. Set Time of Day Pattern Violation'),nl,
        write('        17. Set Driver Pattern Violation'),nl,
        write('        18. Explain Threat'),nl,
        write('        19. New Initialized Vehicle'),nl,
        write('        20. Exit'),nl,nl,
        write('    Enter Number of Action to Perform (followed by .<enter>): '),
        read(C), nl, case(C), !.

% Detect Threat
case(0):- write('Detecting Threat.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             displayThreat(V),
             waitForCont,
             menu.
% New Vehicle
case(1):- write('Creating New Vehicle.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             asserta(monitor(V)),
             write('Vehicle '), write(V), write(' added to KB.'), nl,
             waitForCont,
             menu.
% Display Vehicle Status
case(2):- write('Showing vehicle status.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             displayStatus(V),
             waitForCont,
             menu.
% Set assigned route
case(3):- write('Setting assigned route.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             write('Enter Assigned Route: '), 
         read(R),
             asserta(assignedRoute(V,R)),
             waitForCont,
             menu.
% Set actual route
case(4):- write('Setting actual route.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             write('Enter Actual Route: '), 
         read(R),
             asserta(actualRoute(V,R)),
             waitForCont,
             menu.
% Set expected arrival time
case(5):- write('Setting expected arrival time.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             write('Enter expected arrival time: '), 
         read(T),
             asserta(expectedArrivalTime(V,T)),
             waitForCont,
             menu.
% Set actual arrival time
case(6):- write('Setting actual arrival time.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             write('Enter Actual Arrival Time: '), 
         read(T),
             asserta(actualArrivalTime(V,T)),
             waitForCont,
             menu.
% Set alarm
case(7):- write('Entering an alarm.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             write('Enter Alarm Activation Location: '), 
         read(L),
             write('Enter Alarm Type: '), 
         read(A),
             asserta(alarmOn(V,L,A)),
             waitForCont,
             menu.
% Set traffic jam
case(8):- write('Entering a traffic jam.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             write('Enter Traffic Jam Location: '), 
         read(L),
             asserta(trafficJam(V,L,'Jam')),
             waitForCont,
             menu.
% Set traffic actident.
case(9):- write('Entering a traffic accident.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             write('Enter Traffic Accident Location: '), 
         read(L),
             asserta(trafficAccident(V,L,'Accident')),
             waitForCont,
             menu.
% Set weather delay
case(10):- write('Entering a weather delay.'), nl, 
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Weather Delay Location: '), 
         read(L),
             asserta(weatherDelay(V,L,'WeatherDelay')),
             waitForCont,
             menu.
% Set time deviation
case(11):- write('Entering a time deviation.'), nl, 
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Time Deviation Location: '), 
         read(T),
             asserta(timeDeviation(V,T,'Time Dev.')),
             waitForCont,
             menu.
case(12):- write('Entering a  Port Pattern Violation'),nl,
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Usual Port: '), 
         read(P),
             asserta(usualPort(V,P)),
             write('Enter This Port: '), 
         read(Pt),
             asserta(thisPort(Pt)),
             waitForCont,
             menu.
case(13):- write('Entering a Shipper Pattern Violation'),nl,
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Usual Shipper: '), 
         read(S),
             asserta(usualShipper(V,S)),
             write('Enter Shipper of this trip: '), 
         read(St),
             asserta(thisShipper(St)),
             waitForCont,
             menu.
case(14):- write('Entering a Broker Pattern Violation'),nl,
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Usual Broker: '), 
         read(B),
             asserta(usualBroker(V,B)),
             write('Enter Broker for this trip: '), 
         read(Bt),
             asserta(thisBroker(Bt)),
             waitForCont,
             menu.
case(15):- write('Entering a Cargo Pattern Violation'),nl,
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Usual Cargo: '), 
         read(C),
             asserta(usualCargo(V,C)),
             write('Enter Cargo for this trip: '), 
         read(Ct),
             asserta(thisCargo(Ct)),
             waitForCont,
             menu.
case(16):- write('Entering a Time Pattern Violation'),nl,
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Usual Time (Morning, Evening, Night): '), 
         read(T),
             asserta(usualTime(V,T)),
             write('Enter Time for this trip: '), 
         read(Tt),
             asserta(thisTime(Tt)),
             waitForCont,
             menu.
case(17):- write('Entering a Driver Pattern Violation'),nl,
         write('Enter Vehicle ID: '), 
         read(V),
             write('Enter Usual Driver: '), 
         read(D),
             asserta(usualPort(V,D)),
             write('Enter Driver for this trip: '), 
         read(Dt),
             asserta(thisDriver(Dt)),
             waitForCont,
             menu. 
% Explain Threat
case(18):- write('Explaing Threat.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             why(hasThreat(V)),
             waitForCont,
             menu.
% New Initialized Vehicle
case(19):- write('Creating New Initialized Vehicle.'), nl, 
         write('Enter vehicle Id: '), 
         read(V),
             asserta(monitor(V)),
%
%        Set assigned route
             write('Enter Assigned Route: '), 
         read(R),
             asserta(assignedRoute(V,R)),
%
%        Set actual route
             write('Enter Actual Route: '), 
         read(Ra),
             asserta(actualRoute(V,Ra)),
%
%        Set expected arrival time
             write('Enter expected arrival time: '), 
         read(T),
             asserta(expectedArrivalTime(V,T)),
%
%        Set actual arrival time
             write('Enter Actual Arrival Time: '), 
         read(Ta),
             asserta(actualArrivalTime(V,Ta)),
             waitForCont,
             menu.
% Exiting.
case(20):- write('Exiting TDS...'),nl,nl.



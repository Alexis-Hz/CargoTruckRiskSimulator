% Author: Nick
% Date: 11/5/2008


hasRouteDeviation(truck1).
hasTimeDeviation(truck1).
hasAlarm(truck1).

trafficAccidentEvent(truck1).

justified(truck1).

yellow_light(X) :- hasAnom(X),justified(X).


hasAnom(X) :- (hasRouteDeviation(X);hasTimeDeviation(X);hasAlarm(X)).
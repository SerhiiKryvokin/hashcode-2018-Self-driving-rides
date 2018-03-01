total score: 49,140,674

# solution description
Events describe possible actions in the point of time:
START - assign a ride in earliest time and get the bonus
POSTPONED_START - assign a ride without bonus
STRICT_START - assign a ride now or it will be too late
FINISH - finish a ride and free a vehicle

Lets maintain events in priority queue to poll them in timeline order.
Break ties by event type: 
FINISH comes first to free up a vehicle, 
then START to take rides and take bonus (it worth to play with order to achieve better results in b/c/d)
then POSTPONED_START to take rides as soon as we have a suitable free vehicle
then STRICT_START - last chance to pick up the ride


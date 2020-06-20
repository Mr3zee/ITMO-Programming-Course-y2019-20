node(L, K, V, R, node(L, K, V, Y, R)) :- rand_float(Y).

example(E) :- 
	node(nil, 10, 10, nil, Fir),
	node(nil, 50, 50, nil, Fiv),
	node(nil, 40, 40, Fiv, For),
	node(Fir, 20, 20, nil, Sec),
	node(Sec, 30, 30, For, E).

map_get(node(L, K, V, Y, R), K, V) :- !.
map_get(node(L, K, _, _, _), Key, Value) :- 
	Key < K, 
	map_get(L, Key, Value).
map_get(node(_, K, _, _, R), Key, Value) :- 
	Key > K, 
	map_get(R, Key, Value).

split(nil, _, [nil, nil]) :- !. 
split(node(L, K, V, Y, R), Key, [node(L, K, V, Y, NL), NR]) :- 
	K < Key, 
	split(R, Key, [NL, NR]).
split(node(L, K, V, Y, R), Key, [NL, node(NR, K, V, Y, R)]) :- 
	K >= Key, 
	split(L, Key, [NL, NR]).

merge(nil, B, B).
merge(A, nil, A).
merge(node(L1, K1, V1, Y1, R1), node(L2, K2, V2, Y2, R2), node(L1, K1, V1, Y1, RN)) :- 
	Y1 > Y2, 
	merge(R1, node(L2, K2, V2, Y2, R2), RN).
merge(node(L1, K1, V1, Y1, R1), node(L2, K2, V2, Y2, R2), node(LN, K2, V2, Y2, R2)) :- 
	Y1 =< Y2, 
	merge(node(L1, K1, V1, Y1, R1), L2, LN).

map_put(nil, node(L, K, V, Y, R), node(L, K, V, Y, R)) :- !.
map_put(node(L1, K1, V1, Y1, R1), node(L2, K2, V2, Y2, R2), node(LN, K2, V2, Y2, RN)) :- 
	Y2 > Y1, 
	split(node(L1, K1, V1, Y1, R1), K2, [LN, RN]), !.
map_put(node(L1, K1, V1, Y1, R1), node(L2, K2, V2, Y2, R2), node(LN, K1, V1, Y1, R1)) :- 
	Y2 =< Y1,
	K2 =< K1, 
	map_put(L1, node(L2, K2, V2, Y2, R2), LN).
map_put(node(L1, K1, V1, Y1, R1), node(L2, K2, V2, Y2, R2), node(L1, K1, V1, Y1, RN)) :- 
	Y2 =< Y1,
	K2 > K1, 
	map_put(R1, node(L2, K2, V2, Y2, R2), RN).
map_put(TreeMap, Key, Value, Result) :- map_get(TreeMap, Key, _), map_remove(TreeMap, Key, R), map_put(R, Key, Value, Result), !.
map_put(TreeMap, Key, Value, Result) :- node(nil, Key, Value, nil, NN), map_put(TreeMap, NN, Result).

map_remove(TreeMap, Key, TreeMap) :- \+map_get(TreeMap, Key, _), !.
map_remove(node(L, K, V, Y, R), Key, Result) :-
	Key ==  K,
	merge(L, R, Result), !.
map_remove(node(L, K, V, Y, R), Key, node(LN, K, V, Y, R)) :-
	Key < K,
	map_remove(L, Key, LN).
map_remove(node(L, K, V, Y, R), Key, node(L, K, V, Y, RN)) :-
	Key >= K,
	map_remove(R, Key, RN).

map_build([], R, R) :- !.
map_build([(K, V) | T], Map, Result) :- map_put(Map, K, V, R), map_build(T, R, Result). 
map_build(ListMap, TreeMap) :- map_build(ListMap, nil, TreeMap).

map_floorKey(node(_, K, _, _, _), K, _, K) :- !.
map_floorKey(nil, _, Last, Last) :- Last \= nil, !.
map_floorKey(node(L, K, V, Y, R), Key, Last, FloorKey) :-
	Key < K,
	map_floorKey(L, Key, Last, FloorKey).
map_floorKey(node(L, K, V, Y, R), Key, Last, FloorKey) :-
	Key >= K,
	map_floorKey(R, Key, K, FloorKey).
map_floorKey(Map, Key, FloorKey) :- map_floorKey(Map, Key, nil, FloorKey).

to_node(nil, nil).
to_node(node(L, K, V, Y, R), Res) :- to_node(L, NL), to_node(R, NR), Res =.. [node, NL, V, NR].
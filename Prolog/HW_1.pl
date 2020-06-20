is_not_divider(N, 1) :- !.
is_not_divider(N, D) :- M is mod(N, D), M > 0, D1 is D - 1, is_not_divider(N, D1).
int_sqrt(A, R) :- RD is sqrt(A), R is div(RD, 1).
prime(N) :- int_sqrt(N, S),  is_not_divider(N, S).
composite(N) :- \+ prime(N).
prime_recur(N, F, L, []) :- N == F.
prime_recur(N, F, L, [H | T]) :- L =< H, prime(H), F1 is F * H, prime_recur(N, F1, H, T).
prime_divisors(N, Divisors) :- prime_recur(N, 1, 0, Divisors).
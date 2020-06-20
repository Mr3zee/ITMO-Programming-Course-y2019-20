init(N) :- fill(N, 2).
fill(N, C) :- C1 is C * C, C1 > N, !.
fill(N, C) :- \+prime_table(C, R), S is C * C, add_tables(N, S, C), C1 is C + 1, fill(N, C1).
fill(N, C) :- prime_table(C, R), C1 is C + 1, fill(N, C1).
add_tables(N, S, _) :- N < S, !.
add_tables(N, S, C) :- assert(prime_table(S, C)), S1 is S + C, add_tables(N, S1, C).
int_sqrt(A, R) :- RD is sqrt(A), R is div(RD, 1).

prime(N) :- N > 1, \+prime_table(N, R), !.
composite(N) :- prime_table(N, R), !.

prime_divisors(N, Divisors) :- list(Divisors), check_divisors(N, Divisors).
check_divisors(N, Divisors) :- mult(1, 0, Divisors, R), N == R, !.
check_divisors(N, Divisors) :- mult(1, 0, Divisors, R), N is R, !.
mult(F, L, [], F) :- L \= 0, !.
mult(F, L, [H | T], R) :- L =< H, prime(H), F1 is F * H, mult(F1, H, T, R).

prime_divisors(N, R) :- number(N), int_sqrt(N, S), fill_divisors(N, R).
fill_divisors(1, []) :- !.
fill_divisors(N, [N]) :- \+prime_table(N, R), !.
fill_divisors(N, [H | T]) :- prime_table(N, H), N1 is div(N, H), prime_divisors(N1, T), !.

prime_palindrome(N, K) :- prime(N), to_kth(N, K, R), palindrome(R).
to_kth(0, K, []) :- !.
to_kth(N, K, [H | T]) :- H is mod(N, K), N1 is div(N, K), to_kth(N1, K, T).
palindrome(N) :- reverse(N, N).
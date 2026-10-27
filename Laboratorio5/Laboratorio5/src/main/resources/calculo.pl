% =========================================================
% CALCULO SIMBOLICO CON PROLOG
% Derivadas e integrales
% =========================================================


% =========================================================
% DERIVADAS
% Predicado principal: d(Variable, Funcion, Resultado)
% =========================================================

% Derivada de una constante numerica
d(X, C, 0) :-
    atom(X),
    number(C),
    !.


% Derivada de una variable respecto a si misma
d(X, X, 1) :-
    atom(X),
    !.


% Derivada de una variable multiplicada por una constante
d(X, C*X, C) :-
    atom(X),
    number(C),
    !.


% Derivada de una variable elevada a una potencia
d(X, X^N, N*X^M) :-
    atom(X),
    number(N),
    M is N - 1,
    !.


% Derivada de una constante por una variable elevada a N
d(X, C*X^N, R) :-
    atom(X),
    number(C),
    number(N),
    M is N - 1,
    K is C*N,
    R = K*X^M,
    !.


% Derivada de un logaritmo natural
d(X, log(X), 1/X) :-
    atom(X),
    !.


% Regla de la suma
d(X, A+B, DA+DB) :-
    d(X, A, DA),
    d(X, B, DB),
    !.


% Regla de la resta
d(X, A-B, DA-DB) :-
    d(X, A, DA),
    d(X, B, DB),
    !.


% =========================================================
% INTEGRALES
% Predicado principal: s(Variable, Funcion, Resultado)
% =========================================================

% Integral de una constante numerica
s(X, C, R) :-
    atom(X),
    number(C),
    R = C*X,
    !.


% Integral de x: x^2/2
s(X, X, R) :-
    atom(X),
    R = (X^2)/2,
    !.


% Integral de una constante por x: C*x
s(X, C*X, R) :-
    atom(X),
    number(C),
    K is C/2,
    (K =:= 1 -> R = X^2 ; R = K*X^2),
    !.


% Integral de C*x^N
s(X, C*X^N, R) :-
    atom(X),
    number(C),
    number(N),
    N =\= -1,
    M is N + 1,
    K is C/M,
    (K =:= 1 -> R = X^M ; R = K*X^M),
    !.


% Integral de x^N
s(X, X^N, R) :-
    atom(X),
    number(N),
    N =\= -1,
    M is N + 1,
    R = (X^M)/M,
    !.


% Integral de logaritmo natural
s(X, log(X), R) :-
    atom(X),
    R = X*log(X)-X,
    !.


% Integral de una suma
s(X, A+B, IA+IB) :-
    s(X, A, IA),
    s(X, B, IB),
    !.


% Integral de una resta
s(X, A-B, IA-IB) :-
    s(X, A, IA),
    s(X, B, IB),
    !.
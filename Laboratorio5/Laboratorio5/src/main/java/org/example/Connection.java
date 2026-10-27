package org.example;

import alice.tuprolog.Prolog;
import alice.tuprolog.SolveInfo;
import alice.tuprolog.Theory;

import java.io.InputStream;

public class Connection {

    private final Prolog engine;

    public Connection() throws Exception {
        engine = new Prolog();

        try (InputStream input =
                     getClass().getResourceAsStream("/calculo.pl")) {

            if (input == null) {
                throw new IllegalStateException(
                        "No se encontro el archivo calculo.pl"
                );
            }

            engine.setTheory(new Theory(input));
        }
    }

    public String consultar(String predicado, String expresion)
            throws Exception {

        if (!predicado.equals("d") && !predicado.equals("s")) {
            throw new IllegalArgumentException(
                    "Predicado no permitido"
            );
        }

        if (expresion == null || expresion.isBlank()) {
            throw new IllegalArgumentException(
                    "Debes ingresar una expresion"
            );
        }

        String consulta =
                predicado + "(x, " + expresion + ", R).";

        SolveInfo resultado = engine.solve(consulta);

        if (resultado.isSuccess()) {
            String respuesta = resultado.getTerm("R").toString();
            return MathFormatter.format(respuesta);
        }

        return "Sin solucion para esa expresion";
    }
}
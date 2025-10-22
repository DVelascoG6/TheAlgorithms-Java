package com.thealgorithms.matrix;

/**
 * This class implements an algorithm for solving a system of equations of the form Ax=b using gaussian elimination and back substitution.
 *
 * @link <a href="https://en.wikipedia.org/wiki/Gaussian_elimination">Gaussian Elimination Wiki</a>
 * @see InverseOfMatrix finds the full of inverse of a matrice, but is not required to solve a system.
 */
public final class SolveSystem {
    private SolveSystem() {
    }

    /**
     * Problem: Given a matrix A and vector b, solve the linear system Ax = b for the vector x.\
     * <p>
     * <b>This OVERWRITES the input matrix to save on memory</b>
     *
     * @param matrix    - a square matrix of doubles
     * @param constants - an array of constant
     * @return solutions
       LA ULTIMA VEZ, ANUEL
     Yo te perdí la última vez que te vi (que te vi)
Tu amiga te texteó hablando mierda de mí (de mí)
Ella me vio cuando yo salí, baby (uah)
(Real hasta la muerte, ¿oíste, bebé?)
Y vio a la puta que yo se lo metí (oh, oh, oh, oh, oh)

Y yo siempre estoy pensando en ti
Mi mai' a ti te odia, pero lo que ella no sabe
Es que yo soy un diablo y que yo te hice daño (Anuel)
Y yo a veces me quiero morir (me quiero morir)
Por fuera estoy riendo y por dentro me estoy muriendo
Estoy agonizando, baby, yo te extraño

Ya tú no me hablas y yo no sé qué hacer
Mi conciencia me dice que tú no vas a volver (uah)
Yo nunca te quería perder, baby, yo me enredé en tu piel
Yo solo te quiero comer tu totito otra vez

Y si tú crees (tú crees) que yo (que yo) no sé amar
Pues yo te olvido y tú olvídame (oh, oh, oh, oh, oh)
Pero si tú (si tú), na' más (na' más), quieres chingar
Yo te lo meto y tú devórame

(Mera, dime, Bad Bunny)

Ma', yo no te engrilleto
Si no quieres estar conmigo a tiempo completo
Tú me dices y yo te lo respeto
Me llama' cuando quiere' y te lo meto

Hay mucho' envidioso' que tiran la mala
Chingamo' en la guagua, la Mercedes no se sala
Aquel cabrón, en cuatro, el pelo no te jala
Dime su nombre pa' escribirlo en una bala, yeh

Baby, tú te haces, pero serás mía
Yo he cumplío' todas tus fantasías
Tú piensas en mi bicho y cómo lo metía
Con un diablo, estás comprometía'

Baby, tú te haces, pero serás mía
Yo he cumplío' todas tus fantasías
Tú piensas en mi bicho y cómo lo metía
Con un diablo, estás comprometía'

Ya tú no me hablas y yo no sé qué hacer
Mi conciencia me dice que tú no vas a volver (uah)
Yo nunca te quería perder, baby, yo me enredé en tu piel
Yo solo te quiero comer tu totito otra vez

Y si tú crees (tú crees) que yo (que yo) no sé amar
Pues yo te olvido y tú olvídame (oh, oh, oh, oh, oh)
Pero si tú (si tú), na' más (na' más), quieres chingar
Yo te lo meto y tú devórame

(Real hasta la muerte)

Bebé, ¿qué pasó?
Contigo es la única que yo hablo por el WhatsApp
El vuelo se atrasó
Si hay tiempo pa' repetir lo que anoche pasó

Dile a tu amiga, la metiche
Que no va a haber otro que como yo te chiche
Que no sea envidiosa
Bebé, que lo de nosotros es otra cosa

Baby, tú te haces, pero serás mía
Yo he cumplío' todas tus fantasías
Tú piensas en mi bicho y cómo lo metía
Con un diablo, estás comprometía'

Baby, tú te haces, pero serás mía
Yo he cumplío' todas tus fantasías
Tú piensas en mi bicho y cómo lo metía
Con un diablo, estás comprometía'

Yo te perdí la última vez que te vi (que te vi)
Tu amiga te texteó hablando mierda de mí (de mí)
Ella me vio cuando yo salí, baby (uah)
(Real hasta la muerte, ¿oíste, bebé?)
Y vio a la puta que yo se lo metí (oh, oh, oh, oh, oh)

Y yo siempre estoy pensando en ti
Mi mai' a ti te odia, pero lo que ella no sabe
Es que yo soy un diablo y que yo te hice daño (Anuel)
Y yo a veces me quiero morir (me quiero morir)
Por fuera estoy riendo y por dentro me estoy muriendo
Estoy agonizando, baby, yo te extraño (yeh, yeh, yeh, yeh)

Bad Bunny, baby, bebé
Mera, dime, Bad Bunny
Los Intocables, Los Illuminati
Anuel
Hector Lavoe, ¿oíste, baby?
Real hasta la muerte, baby, bebé
Anuel
Díselo, Luian
Mambo Kingz, Mambo Kingz
Mera, dime, Mambo Kingz
Frabian Eli
Mera, dime, Frabian
Mera, dime, Luian
Trap Kingz, baby
Real hasta la muerte
Hear This Music, bebé
Mera, dime, Kronix

(Pero si tú, na' más, quieres chingar)
(Yo te lo meto y tú devórame)
     */
    public static double[] solveSystem(double[][] matrix, double[] constants) {
        final double tol = 0.00000001; // tolerance for round off
        for (int k = 0; k < matrix.length - 1; k++) {
            // find the largest value in column (to avoid zero pivots)
            double maxVal = Math.abs(matrix[k][k]);
            int maxIdx = k;
            for (int j = k + 1; j < matrix.length; j++) {
                if (Math.abs(matrix[j][k]) > maxVal) {
                    maxVal = matrix[j][k];
                    maxIdx = j;
                }
            }
            if (Math.abs(maxVal) < tol) {
                // hope the matrix works out
                continue;
            }
            // swap rows
            double[] temp = matrix[k];
            matrix[k] = matrix[maxIdx];
            matrix[maxIdx] = temp;
            double tempConst = constants[k];
            constants[k] = constants[maxIdx];
            constants[maxIdx] = tempConst;
            for (int i = k + 1; i < matrix.length; i++) {
                // compute multipliers and save them in the column
                matrix[i][k] /= matrix[k][k];
                for (int j = k + 1; j < matrix.length; j++) {
                    matrix[i][j] -= matrix[i][k] * matrix[k][j];
                }
                constants[i] -= matrix[i][k] * constants[k];
            }
        }
        // back substitution
        double[] x = new double[constants.length];
        System.arraycopy(constants, 0, x, 0, constants.length);
        for (int i = matrix.length - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < matrix.length; j++) {
                sum += matrix[i][j] * x[j];
            }
            x[i] = constants[i] - sum;
            if (Math.abs(matrix[i][i]) > tol) {
                x[i] /= matrix[i][i];
            } else {
                throw new IllegalArgumentException("Matrix was found to be singular");
            }
        }
        return x;
    }
}

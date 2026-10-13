

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class AnalitzadorPrincipal {
    public static void main(String[] args) {

        String classPath = System.getProperty("java.class.path");
        ProcessBuilder pbError = new ProcessBuilder("java", "-cp", classPath, "FiltreLog", "ERROR");
        ProcessBuilder pbWarning = new ProcessBuilder("java", "-cp", classPath, "FiltreLog", "WARNING");
        File arxiuErrors = new File("src\\main\\java\\errors_filtre.log");
        pbError.redirectError(arxiuErrors);
        pbWarning.redirectError(arxiuErrors);

        try {
            Process processError = pbError.start();
            Process processWarning = pbWarning.start();

            String text = "ERROR lga eWARNINGgegea\n lga egeERRORgea\n gesh hojWARNINGWARNINGta eERRORa roahreaj";
            try(BufferedWriter outE = new BufferedWriter(new OutputStreamWriter(processError.getOutputStream()));
                BufferedWriter outW = new BufferedWriter(new OutputStreamWriter(processWarning.getOutputStream()))) {   
                outE.write(text);
                outE.flush();
                outW.write(text);
                outW.flush();
            }

            String respuestaE;
            String respuestaW;
            try(BufferedReader inE = new BufferedReader(new InputStreamReader(processError.getInputStream()));
                BufferedReader inW = new BufferedReader(new InputStreamReader(processWarning.getInputStream()))) {
                respuestaE = inE.readLine();
                respuestaW = inW.readLine();
            }

            int exitEr = processError.waitFor();
            int exitWa = processWarning.waitFor();
            int exitTo = (exitEr == 0 && exitWa == 0) ? 0 : 1;

            System.out.println("RESULTAT: ERRORS=" + respuestaE + " | WARNINGS=" + respuestaW + " | EXIT_CODE=" + exitTo);

        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
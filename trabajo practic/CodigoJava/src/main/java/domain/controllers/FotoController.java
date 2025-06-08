package domain.controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;



public class FotoController {

    public ModelAndView subirFoto(Request request, Response response) {
        Map<String, Object> parametros = new HashMap<>();
        return new ModelAndView(parametros, "probandoFoto.hbs");
    }
    public ModelAndView mostrarFoto(Request request, Response response) throws IOException, ServletException {
        Map<String, Object> parametros = new HashMap<>();
        File uploadDir = new File("../src/main/resources/public/img/imagenesUsuario");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");

        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

        try (InputStream input = request.raw().getPart("uploaded_file").getInputStream()) { // getPart needs to use same "name" as input field in form
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        logInfo(request, tempFile);
        Path path = tempFile.getFileName();
        parametros.put("pathimagen",path);
        System.out.println(parametros.get("pathimagen"));
        return new ModelAndView(parametros, "probandoFoto.hbs");

    }

    private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
        System.out.println("Uploaded file '" + getFileName(req.raw().getPart("uploaded_file")) + "' saved as '" + tempFile.toAbsolutePath() + "'");
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


}

package tpi.g11.alquileres.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class MonedaService {

    public double calcularConversion(String moneda, double cantidad) {
        try {
            URL url = new URL("http://34.82.105.125:8080/convertir");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String requestBody = "{\"moneda_destino\":\"" + moneda + "\",\"importe\":" + cantidad + "}";
            OutputStream os = connection.getOutputStream();
            os.write(requestBody.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Procesar manualmente la respuesta JSON para obtener el valor convertido
                String jsonResponse = response.toString();
                int importeIndex = jsonResponse.indexOf("\"importe\":");
                if (importeIndex != -1) {
                    int valueStartIndex = importeIndex + "\"importe\":".length();
                    int valueEndIndex = jsonResponse.indexOf(",", valueStartIndex);
                    if (valueEndIndex == -1) {
                        valueEndIndex = jsonResponse.indexOf("}", valueStartIndex);
                    }
                    String valorConvertidoStr = jsonResponse.substring(valueStartIndex, valueEndIndex);
                    double valorConvertido = Double.parseDouble(valorConvertidoStr);

                    connection.disconnect();
                    return valorConvertido;
                } else {
                    // Manejar el caso en el que no se encuentra "importe" en la respuesta JSON
                    System.out.println("No se encontró el valor de importe en la respuesta JSON.");
                }
            } else {
                System.out.println("La solicitud no fue exitosa. Código de respuesta: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cantidad; // Devolver la cantidad original en caso de error
    }
}

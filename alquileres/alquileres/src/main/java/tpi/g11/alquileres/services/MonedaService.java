package tpi.g11.alquileres.services;

import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

@Service
public class MonedaService {
    public double calcularConversion(String parametro) {
        CurrencyUnit ars = Monetary.getCurrency("ARS");
        MonetaryAmount montoEnPesos = Monetary.getDefaultAmountFactory().setCurrency(ars).setNumber(1000).create();

        // Conversión a otra moneda
        CurrencyUnit moneda = Monetary.getCurrency(parametro);
        CurrencyConversion conversion = MonetaryConversions.getConversion(moneda);
        MonetaryAmount monto = montoEnPesos.with(conversion);

        return monto.getNumber().doubleValue();
    }
}
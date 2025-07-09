package com.example.DIRECCIONES.config;

import java.util.List;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.DIRECCIONES.model.Comunas;
import com.example.DIRECCIONES.model.Direcciones;
import com.example.DIRECCIONES.model.Regiones;
import com.example.DIRECCIONES.repository.DireccionesRepository;
import com.example.DIRECCIONES.repository.RegionesRepository;
import com.example.DIRECCIONES.repository.ComunasRepository;

@Configuration
public class LoadDataConfig {

    @Bean
    CommandLineRunner initDatabase(
            DireccionesRepository direccionesRepository,
            RegionesRepository regionesRepository,
            ComunasRepository comunasRepository) {
        return args -> {

            //
            if (regionesRepository.count() == 0) {
                Regiones regionMetropolitana = new Regiones();
                regionMetropolitana.setNombre("Región Metropolitana");
                regionMetropolitana.setComunas(new ArrayList<>());
                
                Regiones regionValparaiso = new Regiones();
                regionValparaiso.setNombre("Región de Valparaíso");
                regionValparaiso.setComunas(new ArrayList<>());
                
                Regiones regionBiobio = new Regiones();
                regionBiobio.setNombre("Región del Biobío");
                regionBiobio.setComunas(new ArrayList<>());
                
                Regiones regionAntofagasta = new Regiones();
                regionAntofagasta.setNombre("Región de Antofagasta");
                regionAntofagasta.setComunas(new ArrayList<>());
                
                regionesRepository.saveAll(List.of(regionMetropolitana, regionValparaiso, regionBiobio, regionAntofagasta));
                System.out.println("Regiones precargadas.");
            }

            // 2. Cargar comunas si no existen
            if (comunasRepository.count() == 0) {
                Regiones regionMetropolitana = regionesRepository.findByNombre("Región Metropolitana").get();
                Regiones regionValparaiso = regionesRepository.findByNombre("Región de Valparaíso").get();
                Regiones regionBiobio = regionesRepository.findByNombre("Región del Biobío").get();
                Regiones regionAntofagasta = regionesRepository.findByNombre("Región de Antofagasta").get();

                Comunas comunaQuilicura = new Comunas();
                comunaQuilicura.setNombre("Quilicura");
                comunaQuilicura.setRegion(regionMetropolitana);
                comunaQuilicura.setDirecciones(new ArrayList<>());
                
                Comunas comunaRecoleta = new Comunas();
                comunaRecoleta.setNombre("Recoleta");
                comunaRecoleta.setRegion(regionMetropolitana);
                comunaRecoleta.setDirecciones(new ArrayList<>());
                
                Comunas comunaRenca = new Comunas();
                comunaRenca.setNombre("Renca");
                comunaRenca.setRegion(regionMetropolitana);
                comunaRenca.setDirecciones(new ArrayList<>());
                
                Comunas comunaConcepcion = new Comunas();
                comunaConcepcion.setNombre("Concepción");
                comunaConcepcion.setRegion(regionBiobio);
                comunaConcepcion.setDirecciones(new ArrayList<>());
                
                Comunas comunaChillan = new Comunas();
                comunaChillan.setNombre("Chillán");
                comunaChillan.setRegion(regionBiobio);
                comunaChillan.setDirecciones(new ArrayList<>());
                
                Comunas comunaAntofagasta = new Comunas();
                comunaAntofagasta.setNombre("Antofagasta");
                comunaAntofagasta.setRegion(regionAntofagasta);
                comunaAntofagasta.setDirecciones(new ArrayList<>());
                
                Comunas comunaCalama = new Comunas();
                comunaCalama.setNombre("Calama");
                comunaCalama.setRegion(regionAntofagasta);
                comunaCalama.setDirecciones(new ArrayList<>());
                
                Comunas comunaValparaiso = new Comunas();
                comunaValparaiso.setNombre("Valparaíso");
                comunaValparaiso.setRegion(regionValparaiso);
                comunaValparaiso.setDirecciones(new ArrayList<>());
                
                Comunas comunaViñaDelMar = new Comunas();
                comunaViñaDelMar.setNombre("Viña del Mar");
                comunaViñaDelMar.setRegion(regionValparaiso);
                comunaViñaDelMar.setDirecciones(new ArrayList<>());

                comunasRepository.saveAll(List.of(
                        comunaQuilicura, comunaRecoleta, comunaRenca,
                        comunaConcepcion, comunaChillan,
                        comunaAntofagasta, comunaCalama,
                        comunaValparaiso, comunaViñaDelMar
                ));
                System.out.println("Comunas precargadas.");
            }

            // 3. Cargar direcciones si no existen
            if (direccionesRepository.count() == 0) {
                // Obtener comunas y regiones ya guardadas
                Comunas comunaQuilicura = comunasRepository.findByNombre("Quilicura").get();
                Comunas comunaConcepcion = comunasRepository.findByNombre("Concepción").get();
                Comunas comunaAntofagasta = comunasRepository.findByNombre("Antofagasta").get();
                Comunas comunaValparaiso = comunasRepository.findByNombre("Valparaíso").get();
                Comunas comunaViñaDelMar = comunasRepository.findByNombre("Viña del Mar").get();
                Comunas comunaChillan = comunasRepository.findByNombre("Chillán").get();

                Regiones regionMetropolitana = regionesRepository.findByNombre("Región Metropolitana").get();
                Regiones regionBiobio = regionesRepository.findByNombre("Región del Biobío").get();
                Regiones regionAntofagasta = regionesRepository.findByNombre("Región de Antofagasta").get();
                Regiones regionValparaiso = regionesRepository.findByNombre("Región de Valparaíso").get();

                Direcciones direccion1 = new Direcciones();
                direccion1.setCalle("Av. Mar");
                direccion1.setNumero("24522");
                direccion1.setDetalle("Santiago");
                direccion1.setComuna(comunaQuilicura);
                direccion1.setRegion(regionMetropolitana);
                
                Direcciones direccion2 = new Direcciones();
                direccion2.setCalle("Av. Libertador");
                direccion2.setNumero("12345");
                direccion2.setDetalle("Concepción");
                direccion2.setComuna(comunaConcepcion);
                direccion2.setRegion(regionBiobio);
                
                Direcciones direccion3 = new Direcciones();
                direccion3.setCalle("Av. Los Pajaritos");
                direccion3.setNumero("67890");
                direccion3.setDetalle("Antofagasta");
                direccion3.setComuna(comunaAntofagasta);
                direccion3.setRegion(regionAntofagasta);
                
                Direcciones direccion4 = new Direcciones();
                direccion4.setCalle("Av. San Martín");
                direccion4.setNumero("54321");
                direccion4.setDetalle("Valparaíso");
                direccion4.setComuna(comunaValparaiso);
                direccion4.setRegion(regionValparaiso);
                
                Direcciones direccion5 = new Direcciones();
                direccion5.setCalle("Av. Los Castaños");
                direccion5.setNumero("98765");
                direccion5.setDetalle("Viña del Mar");
                direccion5.setComuna(comunaViñaDelMar);
                direccion5.setRegion(regionValparaiso);
                
                Direcciones direccion6 = new Direcciones();
                direccion6.setCalle("Av. Los Robles");
                direccion6.setNumero("45678");
                direccion6.setDetalle("Chillán");
                direccion6.setComuna(comunaChillan);
                direccion6.setRegion(regionBiobio);

                direccionesRepository.saveAll(List.of(direccion1, direccion2, direccion3, direccion4, direccion5, direccion6));
                System.out.println("Direcciones precargadas.");
                System.out.println("Datos iniciales cargados");
            } else {
                System.out.println("Los datos ya existen. No se cargaron nuevos datos.");
            }
        };
    }
}

package com.gft.mvcproject.components;

import com.gft.mvcproject.entities.*;
import com.gft.mvcproject.services.*;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataBaseInitializer {
    @Autowired
    private CargoService cargoService;
    @Autowired
    private DailyService dailyService;
    @Autowired
    private GrupoService grupoService;
    @Autowired
    private ModuloService moduloService;
    @Autowired
    private ProgramaStartService programaStartService;
    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private StarterService starterService;
    @Autowired
    private TecnologiaService tecnologiaService;
    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostConstruct
    public void init() throws ParseException {
        boolean isNewDb;

        try {
            cargoService.getCargo(1);
            isNewDb = false;
        } catch (NotFoundException e) {
            isNewDb = true;
        }

        if (isNewDb) {
            // Cria cargos 'sm' and 'adm'
            Cargo cargoAdm = new Cargo("adm");
            Cargo cargoSm = new Cargo("sm");
            cargoAdm = cargoService.saveCargo(cargoAdm);
            cargoSm = cargoService.saveCargo(cargoSm);

            // Cria usuário Admin inicial e Dois Scrum Masters
            String encodedPasswordAdm = passwordEncoder.encode("Gft2021");
            String encodedPasswordSm1 = passwordEncoder.encode("GftSm01");
            String encodedPasswordSm2 = passwordEncoder.encode("GftSm02");
            User adm1 = new User("clecio.silva@gft.com", encodedPasswordAdm, true, cargoAdm);
            User adm2 = new User("Admin", encodedPasswordAdm, true, cargoAdm);
            User sm1 = new User("GODL", encodedPasswordSm1, true, cargoSm);
            User sm2 = new User("LEOD", encodedPasswordSm2, true, cargoSm);
            userService.saveUser(adm1);
            userService.saveUser(adm2);
            sm1 = userService.saveUser(sm1);
            sm2 = userService.saveUser(sm2);

            // Cria Programa Start
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            ProgramaStart ps1 = new ProgramaStart("Turma 4", format.parse("2021-02-02"), format.parse("2021-05-03"));
            ProgramaStart ps2 = new ProgramaStart("Turma 5", format.parse("2021-10-02"), format.parse("2022-01-03"));
            ps1 = programaStartService.saveProgramaStart(ps1);
            ps2 = programaStartService.saveProgramaStart(ps2);

            // Cria Starters
            Starter starter1 = new Starter("Sabrina", "SABE", ps1);
            Starter starter2 = new Starter("Eduardo", "EDUS", ps1);
            Starter starter3 = new Starter("Rafael", "RAFL", ps2);
            Starter starter4 = new Starter("Patrícia", "PATC", ps2);
            starter1 = starterService.saveStarter(starter1);
            starter2 = starterService.saveStarter(starter2);
            starter3 = starterService.saveStarter(starter3);
            starter4 = starterService.saveStarter(starter4);

            // Cria Tecnologias
            Tecnologia java = new Tecnologia("Java", "O Java Original");
            Tecnologia dotNet = new Tecnologia(".Net", "Tecnologia secundária");
            java = tecnologiaService.saveTecnologia(java);
            dotNet = tecnologiaService.saveTecnologia(dotNet);

            // Cria Grupos
            List<Starter> starters1 = Arrays.asList(starter1, starter2);
            List<Starter> starters2 = Arrays.asList(starter3, starter4);
            Grupo grupoSm1 = new Grupo(java, starters1, sm1);
            Grupo grupoSm2 = new Grupo(dotNet, starters2, sm2);
            grupoService.saveGrupo(grupoSm1);
            grupoService.saveGrupo(grupoSm2);

            // Cria Dailies
            Daily daily1S1 = new Daily(format.parse("2021-02-04"), "Treinamento", "Video 07", "Nenhum", true, starter1);
            Daily daily2S1 = new Daily(format.parse("2021-02-05"), "Pretende chegar video 14", "Video 10", "Nenhum", true, starter1);
            Daily daily3S1 = new Daily(format.parse("2021-02-16"), "", "", "", false, starter1);
            Daily daily1S2 = new Daily(format.parse("2021-02-04"), "Treinamento", "Video 04", "Nenhum", true, starter2);
            Daily daily2S2 = new Daily(format.parse("2021-02-05"), "Pretende chegar video 10", "Video 6", "Nenhum", true, starter2);
            Daily daily3S2 = new Daily(format.parse("2021-02-16"), "Implementação do sistema de pesquisa", "Relatórios", "Nenhum", true, starter2);
            Daily daily1S3 = new Daily(format.parse("2021-10-03"), "", "", "", false, starter3);
            Daily daily2S3 = new Daily(format.parse("2021-10-04"), "", "", "", false, starter3);
            Daily daily3S3 = new Daily(format.parse("2021-10-15"), "Implementação do upload de fotos", "Limpeza de código", "Nenhum", true, starter3);
            Daily daily1S4 = new Daily(format.parse("2021-10-03"), "Pretende chegar video 15", "Video 06", "Nenhum", true, starter4);
            Daily daily2S4 = new Daily(format.parse("2021-10-04"), "Pretende chegar video 23", "Video 14", "Nenhum", true, starter4);
            Daily daily3S4 = new Daily(format.parse("2021-10-15"), "Revisão de código", "Terminou projeto", "Nenhum", true, starter4);
            daily1S1 = dailyService.saveDaily(daily1S1);
            daily2S1 = dailyService.saveDaily(daily2S1);
            daily3S1 = dailyService.saveDaily(daily3S1);
            daily1S2 = dailyService.saveDaily(daily1S2);
            daily2S2 = dailyService.saveDaily(daily2S2);
            daily3S2 = dailyService.saveDaily(daily3S2);
            daily1S3 = dailyService.saveDaily(daily1S3);
            daily2S3 = dailyService.saveDaily(daily2S3);
            daily3S3 = dailyService.saveDaily(daily3S3);
            daily1S4 = dailyService.saveDaily(daily1S4);
            daily2S4 = dailyService.saveDaily(daily2S4);
            daily3S4 = dailyService.saveDaily(daily3S4);

            // Cria Projetos
            Projeto p1 = new Projeto(90, "MVC", starter1);
            Projeto p2 = new Projeto(78, "MVC", starter2);
            Projeto p3 = new Projeto(87, "MVC", starter3);
            Projeto p4 = new Projeto(100, "MVC", starter4);
            p1 = projetoService.saveProjeto(p1);
            p2 = projetoService.saveProjeto(p2);
            p3 = projetoService.saveProjeto(p3);
            p4 = projetoService.saveProjeto(p4);

            // Cria módulos
            List<Daily> dailiesEstudo1 = Arrays.asList(daily1S1, daily1S2, daily2S1, daily2S2);
            List<Daily> dailiesDesafio1 = Arrays.asList(daily3S1, daily3S2);
            List<Daily> dailiesEstudo2 = Arrays.asList(daily1S3, daily1S4, daily2S3, daily2S4);
            List<Daily> dailiesDesafio2 = Arrays.asList(daily3S3, daily3S4);
            List<Projeto> projetos1 = Arrays.asList(p1, p2);
            List<Projeto> projetos2 = Arrays.asList(p3, p4);
            Modulo mvcEstudo1 = new Modulo("MVC Estudo - Turma 4", dailiesEstudo1, new ArrayList<>());
            Modulo mvcDesafio1 = new Modulo("MVC Desafio - Turma 4", dailiesDesafio1, projetos1);
            Modulo mvcEstudo2 = new Modulo("MVC Estudo - Turma 5", dailiesEstudo2, new ArrayList<>());
            Modulo mvcDesafio2 = new Modulo("MVC Desafio - Turma 5", dailiesEstudo2, projetos2);
            moduloService.saveModulo(mvcEstudo1);
            moduloService.saveModulo(mvcDesafio1);
            moduloService.saveModulo(mvcEstudo2);
        }
    }
}

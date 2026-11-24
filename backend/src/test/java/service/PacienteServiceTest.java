package service;


import static org.assertj.core.api.Assertions.assertThat;

import com.clinicavida.dto.EstatisticasResponse;
import com.clinicavida.dto.PacienteRequest;
import com.clinicavida.dto.PacienteResponse;
import com.clinicavida.repository.PacienteRepositoryEmMemoria;
import com.clinicavida.service.PacienteService;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacienteServiceTest {

    private PacienteService service;

    @BeforeEach
    void setUp() {
        service = new PacienteService(new PacienteRepositoryEmMemoria());
    }

    private void cadastrarTres() {
        service.cadastrar(new PacienteRequest("João Silva", 45, "(11) 99999-9999"));
        service.cadastrar(new PacienteRequest("Álvaro Lima", 33, "(11) 98888-7777"));
        service.cadastrar(new PacienteRequest("Ana Costa", 28, "(21) 97777-6666"));
    }

    @Test
    void cadastrarAtribuiIdETiraEspacosDoNome() {
        PacienteResponse r = service.cadastrar(
                new PacienteRequest("  Maria Souza  ", 40, "(11) 91111-2222"));

        assertThat(r.id()).isEqualTo(1L);
        assertThat(r.nome()).isEqualTo("Maria Souza");
    }

    @Test
    void listarOrdenaPorNomeIgnorandoAcento() {
        cadastrarTres();

        List<PacienteResponse> lista = service.listar(null);

        assertThat(lista).extracting(PacienteResponse::nome)
                .containsExactly("Álvaro Lima", "Ana Costa", "João Silva");
    }

    @Test
    void buscaIgnoraAcentoEMaiuscula() {
        cadastrarTres();

        List<PacienteResponse> lista = service.listar("ALVARO");

        assertThat(lista).extracting(PacienteResponse::nome).containsExactly("Álvaro Lima");
    }

    @Test
    void buscaPorParteDoNome() {
        cadastrarTres();

        assertThat(service.listar("silva")).extracting(PacienteResponse::nome)
                .containsExactly("João Silva");
    }

    @Test
    void buscaSemResultadoRetornaListaVazia() {
        cadastrarTres();

        assertThat(service.listar("zzz")).isEmpty();
    }

    @Test
    void estatisticasComListaVazia() {
        EstatisticasResponse e = service.estatisticas();

        assertThat(e.total()).isZero();
        assertThat(e.idadeMedia()).isNull();
        assertThat(e.maisNovo()).isNull();
        assertThat(e.maisVelho()).isNull();
    }

    @Test
    void estatisticasComPacientes() {
        cadastrarTres();

        EstatisticasResponse e = service.estatisticas();

        assertThat(e.total()).isEqualTo(3);
        assertThat(e.idadeMedia()).isEqualTo(35.3);
        assertThat(e.maisNovo().nome()).isEqualTo("Ana Costa");
        assertThat(e.maisVelho().nome()).isEqualTo("João Silva");
    }
}
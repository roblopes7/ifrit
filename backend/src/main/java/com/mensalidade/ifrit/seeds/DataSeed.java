package com.mensalidade.ifrit.seeds;

import com.mensalidade.ifrit.dto.request.UsuarioRequest;
import com.mensalidade.ifrit.dto.response.UsuarioCompletoResponse;
import com.mensalidade.ifrit.models.Usuario;
import com.mensalidade.ifrit.models.enums.Perfil;
import com.mensalidade.ifrit.services.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeed  implements CommandLineRunner {

    private static final String SENHA_PADRAO = "root";
    private static final String USUARIO_PADRAO = "ADMIN";

    private final UsuarioService usuarioService;

    public DataSeed(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        gerarUsuarios();
    }

    private void gerarUsuarios() {
        UsuarioCompletoResponse usuario = usuarioService.findByLogin(USUARIO_PADRAO);
        if(usuario == null){
            UsuarioRequest request = new UsuarioRequest();
            request.setNome(USUARIO_PADRAO);
            request.setLogin(USUARIO_PADRAO);
            request.setSenha(SENHA_PADRAO);
            request.setPerfil(Perfil.ADMIN);
            request.setAtivo(true);

            usuarioService.cadastrarUsuario(request);
        }
    }
}

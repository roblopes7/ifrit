package com.mensalidade.ifrit.utils;

import com.mensalidade.ifrit.dto.request.UsuarioRequest;
import com.mensalidade.ifrit.models.Usuario;
import com.mensalidade.ifrit.models.enums.Perfil;

import java.util.Arrays;
import java.util.List;

public class UsuarioTest {

    TestsUtil testsUtil = new TestsUtil();

    public UsuarioRequest criarUsuario(){
        UsuarioRequest u = new UsuarioRequest();
        u.setId(testsUtil.getUiidPadrao());
        u.setAtivo(true);
        u.setLogin("TESTE");
        u.setNome("Usuario Teste");
        u.setPerfil(Perfil.ADMIN);
        u.setSenha("123456");
        return u;
    }

    public List<Usuario> usuarios(){
        return  Arrays.asList(
                new Usuario(testsUtil.getUiidPadrao(), "TESTE", "Usuario Teste", "123456", "User@mail.com", "", "", Perfil.ADMIN, true),
                new Usuario(testsUtil.getUiidDiferente(), "TESTE 2", "Usuario Teste 2", "123456", "User2@mail.com", "", "", Perfil.ADMIN, true)
        );
    }
}

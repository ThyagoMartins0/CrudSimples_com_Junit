package com.crudzin.demo.controller;

import com.crudzin.demo.model.Produto;
import com.crudzin.demo.controller.Service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoControllerTeste.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProdutos() throws Exception {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        produto1.setNome("Produto 1");
        produto1.setPreco(10.0);

        Produto produto2 = new Produto();
        produto2.setId(2L);
        produto2.setNome("Produto 2");
        produto2.setPreco(20.0);

        given(produtoService.getAllProdutos()).willReturn(Arrays.asList(produto1, produto2));

        mockMvc.perform(get("/api/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Produto 1"))
                .andExpect(jsonPath("$[1].nome").value("Produto 2"));
    }

    @Test
    void testGetProdutoById() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto 1");
        produto.setPreco(10.0);

        given(produtoService.getProdutoById(anyLong())).willReturn(Optional.of(produto));

        mockMvc.perform(get("/api/produtos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto 1"));
    }

    @Test
    void testCreateProduto() throws Exception {
        Produto produto = new Produto();
        produto.setNome("Produto 1");
        produto.setPreco(10.0);

        given(produtoService.createProduto(any(Produto.class))).willReturn(produto);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto 1"));
    }

    @Test
    void testUpdateProduto() throws Exception {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto 1");
        produto.setPreco(10.0);

        given(produtoService.updateProduto(anyLong(), any(Produto.class))).willReturn(produto);

        mockMvc.perform(put("/api/produtos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto 1"));
    }

    @Test
    void testDeleteProduto() throws Exception {
        doNothing().when(produtoService).deleteProduto(anyLong());

        mockMvc.perform(delete("/api/produtos/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}

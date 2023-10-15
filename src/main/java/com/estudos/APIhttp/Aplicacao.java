package com.estudos.APIhttp;

import okhttp3.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Aplicacao extends JFrame {

    private final OkHttpClient client = new OkHttpClient();

    public Aplicacao() {
        setLayout(new FlowLayout());

        JButton getButton = new JButton("Buscar Produtos");
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Request request = new Request.Builder()
                        .url("http://localhost:8080/produtos")
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    System.out.println(response.body().string());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JTextField nomeField = new JTextField(10);
        JTextField precoField = new JTextField(10);
        JButton postButton = new JButton("Adicionar Produto");
        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String json = "{\"nome\":\"" + nomeField.getText() + "\", \"preco\":" + precoField.getText() + "}";

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"), json);

                Request request = new Request.Builder()
                        .url("http://localhost:8080/produtos")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    System.out.println(response.body().string());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(getButton);
        add(new JLabel("Nome:"));
        add(nomeField);
        add(new JLabel("Preço:"));
        add(precoField);
        add(postButton);

        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Aplicacao().setVisible(true);
            }
        });
    }

}

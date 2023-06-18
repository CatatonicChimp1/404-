package org.example.forms;

import javax.swing.*;

public class RestorePassword extends JFrame {
    RestorePassword(){
        JFrame frame = new JFrame();
        frame.setTitle("Восстановление пароля");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);

        JLabel youLogin = new JLabel("Логин");
        JTextField loginField = new JTextField();
        JLabel secretQuestion = new JLabel("Секретный вопрос");
        JTextField secretQuestionField = new JTextField();
        JLabel answerSecretQuestion = new JLabel("Ответ на секретный вопрос");
        JTextField answerSecretQuestionField  = new JTextField();
        JButton restoreButton = new JButton("Восстановить");

        JPanel restorePanel = new JPanel();
        restorePanel.setLayout(new BoxLayout(restorePanel, BoxLayout.Y_AXIS));

        restorePanel.add(youLogin);
        restorePanel.add(loginField);
        restorePanel.add(secretQuestion);
        restorePanel.add(secretQuestionField);
        restorePanel.add(answerSecretQuestion);
        restorePanel.add(answerSecretQuestionField);
        restorePanel.add(restoreButton);

        restoreButton.addActionListener(e->{
            String login = loginField.getText().toString();
            String question = secretQuestionField.getText().toString();
            String answer = answerSecretQuestionField.getText().toString();


        });

        frame.add(restorePanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        RestorePassword restorePassword = new RestorePassword();
    }
}

package com.marvelapp06.marvelapp.quiz

import com.marvelapp06.marvelapp.R

object Constants {

    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()

        // 1
        val que1 = Question(
                1,
                "Qual o nome desse personagem?",
                R.drawable.image1_quiz,
                "Homem-aranha",
                "Homem-formiga",
                "Homem de ferro",
                "Capitão aço",
                "Hulk",
                3
        )

        questionsList.add(que1)

        // 2
        val que2 = Question(
                2, "Qual o criador/escritor da seguinte HQ:",
                R.drawable.image2_quiz,
                "Henry Abrams", "Oliver Coipel",
                "Pelé", "Christopher Cantwell","Pat Davidson", 4
        )

        questionsList.add(que2)

        // 3
        val que3 = Question(
                3, "Quando ocorreu o lançamento da série “Thor & Hulk”?",
                R.drawable.image3_quiz,
                "1990", "2030",
                "2017", "2015","1987", 3
        )

        questionsList.add(que3)

        // 4
        val que4 = Question(
                4, "Como o Hulk adquiriu a forma “gigante esmeralda”?",
                R.drawable.image4_quiz,
                "Foi atingido por raios gama", "Usou anabolizantes",
                "Foi atingido por raios grama",
                "Foi atingindo por raios beta",
                "Tomou pílulas de crescimento", 1
        )

        questionsList.add(que4)

        // 5
        val que5 = Question(
                5, "Qual o nome do martelo Thor?",
                R.drawable.image5_quiz,
                "Vanir", "Martelo do Thor",
                "Mjolnir", "Norn","Argos", 3
        )

        questionsList.add(que5)

        // 6
        val que6 = Question(
                6, "Qual o verdadeiro nome do Deadpool?",
                R.drawable.image6_quiz,
                "Wade Robson", "Wade Williams",
                "Wade da Silva", "Wade Wilson","Wade Johnson", 4
        )

        questionsList.add(que6)

        // 7
        val que7 = Question(
                7, "Do que é feito o escudo do Capitão América?",
                R.drawable.image7_quiz,
                "Adamantium", "Vibranium",
                "Promécio", "Dolomite","Hemalite", 2
        )

        questionsList.add(que7)

        // 8
        val que8 = Question(
                8, "Qual é o verdadeiro nome da Pantera Negra?",
                R.drawable.image8_quiz,
                "T'Challa", "M'Baku",
                "N'Jadaka", "N'Jobu","Nyong'o",1
        )

        questionsList.add(que8)

        // 9
        val que9 = Question(
                9, "Que tipo de médico é Stephen Strange?",
                R.drawable.image9_quiz,
                "Neurocirurgião", "Cirurgião cardiotorácico",
                "Cirurgião de trauma", "Cirurgião Plástico","Ginecologista", 1
        )

        questionsList.add(que9)

        // 10
        val que10 = Question(
                10, "Qual é o nome do universo microscópico que o Homem-Formiga viaja quando se torna subatômico?",
                R.drawable.image10_quiz,
                "Submundo", "Reino Quântico",
                "Universo paralelo", "Universo Quântico","Reino Sculptor", 2
        )

        questionsList.add(que10)

        return questionsList
    }
}

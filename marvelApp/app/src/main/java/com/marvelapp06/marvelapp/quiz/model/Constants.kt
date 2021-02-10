package com.marvelapp06.marvelapp.quiz.model

import com.marvelapp06.marvelapp.R

object Constants {

    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {

        val questionsList = ArrayList<Question>()

        // 1
        val que1 = Question(
                1,
                "What is the name of this character?",
                R.drawable.image1_quiz,
                "Spider-man",
                "Ant-man",
                "Iron-man",
                "Steel Captain",
                "Hulk",
                3
        )

        questionsList.add(que1)

        // 2
        val que2 = Question(
                2,
                    "What the creator of HQ:",
                R.drawable.image2_quiz,
                "Henry Abrams", "Oliver Coipel",
                "Pelé", "Christopher Cantwell","Pat Davidson", 4
        )

        questionsList.add(que2)

        // 3
        val que3 = Question(
                3, "When the “Thor & Hulk” series was launched?",
                R.drawable.image3_quiz,
                "1990", "2030",
                "2017", "2015","1987", 3
        )

        questionsList.add(que3)

        // 4
        val que4 = Question(
                4, "How did the Hulk acquire the “emerald giant” shape?",
                R.drawable.image4_quiz,
                "Got hit by gamma rays", "Used anabolic steroids",
                        "Was struck by grass rays",
                "It was reaching by beta rays",
                "Took growth pills", 1
        )

        questionsList.add(que4)

        // 5
        val que5 = Question(
                5,
                    "What is the name of the Thor hammer?",

                R.drawable.image5_quiz,
                "Vanir", "Thor's Hammer",
                "Mjolnir", "Norn","Argos", 3
        )

        questionsList.add(que5)

        // 6
        val que6 = Question(
                6,
                    "What is the real name of Deadpool?",
                R.drawable.image6_quiz,
                "Wade Robson", "Wade Williams",
                "Wade James", "Wade Wilson","Wade Johnson", 4
        )

        questionsList.add(que6)

        // 7
        val que7 = Question(
                7, "What Captain America's shield is made of?",
                R.drawable.image7_quiz,
                "Adamantium", "Vibranium",
                "Pomethium", "Dolomite","Hemalite", 2
        )

        questionsList.add(que7)

        // 8
        val que8 = Question(
                8, "What is the real name of Pantera Negra?",
                R.drawable.image8_quiz,
                "T'Challa", "M'Baku",
                "N'Jadaka", "N'Jobu","Nyong'o",1
        )

        questionsList.add(que8)

        // 9
        val que9 = Question(
                9,
                    "What kind of doctor is Stephen Strange?",
                R.drawable.image9_quiz,
                "Neurosurgeon", "Cardiothoracic surgeon",
                "Trauma surgeon", "Plastic surgeon","Gynecologist", 1
        )

        questionsList.add(que9)

        // 10
        val que10 = Question(
                10,
                    "What is the name of the microscopic universe that Ant-Man travels when he becomes subatomic?",
                R.drawable.image10_quiz,
                "Underworld", "Quantum Realm",
                "Parallel universe", "Quantum universe","Kingdom Sculptor", 2
        )

        questionsList.add(que10)

        return questionsList
    }
}

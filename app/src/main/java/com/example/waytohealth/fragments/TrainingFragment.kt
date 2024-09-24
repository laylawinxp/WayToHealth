package com.example.waytohealth.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.waytohealth.DBHelper
import com.example.waytohealth.R
import java.util.Calendar

class TrainingFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_training, container, false)
    }

    @SuppressLint("InflateParams")
    override fun onResume() {
        super.onResume()

        val balancingIB = requireView().findViewById<ImageButton>(R.id.balancing_ib)
        val flexibilityIB = requireView().findViewById<ImageButton>(R.id.flexibility_ib)
        val powerIB = requireView().findViewById<ImageButton>(R.id.power_ib)
        val easyIB = requireView().findViewById<ImageButton>(R.id.easy_ib)
        val normIB = requireView().findViewById<ImageButton>(R.id.norm_ib)
        val hardIB = requireView().findViewById<ImageButton>(R.id.hard_ib)

        val balancingTXT = requireView().findViewById<TextView>(R.id.balancing_txt)
        val flexibilityTXT = requireView().findViewById<TextView>(R.id.flexibility_txt)
        val powerTXT = requireView().findViewById<TextView>(R.id.power_txt)
        val easyTXT = requireView().findViewById<TextView>(R.id.easy_txt)
        val normTXT = requireView().findViewById<TextView>(R.id.norm_txt)
        val hardTXT = requireView().findViewById<TextView>(R.id.hard_txt)

        var type = "balancing"
        var level = "easy"

        balancingIB.setOnClickListener {
            balancingIB.setImageResource(R.drawable.balancing_selected)
            flexibilityIB.setImageResource(R.drawable.flexibility)
            powerIB.setImageResource(R.drawable.power)

            balancingTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            flexibilityTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            powerTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            type = "balancing"
        }

        flexibilityIB.setOnClickListener {
            balancingIB.setImageResource(R.drawable.balancing)
            flexibilityIB.setImageResource(R.drawable.flexibility_selected)
            powerIB.setImageResource(R.drawable.power)

            balancingTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            flexibilityTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            powerTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            type = "flexibility"
        }

        powerIB.setOnClickListener {
            balancingIB.setImageResource(R.drawable.balancing)
            flexibilityIB.setImageResource(R.drawable.flexibility)
            powerIB.setImageResource(R.drawable.power_selected)

            balancingTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            flexibilityTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            powerTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

            type = "power"
        }

        easyIB.setOnClickListener {
            easyIB.setImageResource(R.drawable.easy_selected)
            normIB.setImageResource(R.drawable.norm)
            hardIB.setImageResource(R.drawable.hard)

            easyTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            normTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            hardTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            level = "easy"
        }

        normIB.setOnClickListener {
            easyIB.setImageResource(R.drawable.easy)
            normIB.setImageResource(R.drawable.norm_selected)
            hardIB.setImageResource(R.drawable.hard)

            easyTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            normTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            hardTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))

            level = "norm"
        }

        hardIB.setOnClickListener {
            easyIB.setImageResource(R.drawable.easy)
            normIB.setImageResource(R.drawable.norm)
            hardIB.setImageResource(R.drawable.hard_selected)

            easyTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            normTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.pink))
            hardTXT.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

            level = "hard"
        }

        val buttStart: Button = requireView().findViewById(R.id.start)

        buttStart.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.training_layout, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            myDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            myDialog.show()

            val trainingName: TextView = dialogBinding.findViewById(R.id.trainingName)

            val text1: TextView = dialogBinding.findViewById(R.id.text1)
            val text2: TextView = dialogBinding.findViewById(R.id.text2)
            val text3: TextView = dialogBinding.findViewById(R.id.text3)

            val img1: ImageView = dialogBinding.findViewById(R.id.img1)
            val img2: ImageView = dialogBinding.findViewById(R.id.img2)
            val img3: ImageView = dialogBinding.findViewById(R.id.img3)

            val sharedPrefs =
                requireContext().getSharedPreferences("TypePrefs", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                if (type == "balancing") {
                    putString("balancing", true.toString())
                } else if (type == "power") {
                    putString("power", true.toString())
                } else if (type == "flexibility") {
                    putString("flexibility", true.toString())
                }
                apply()
            }

            if (type == "balancing" && level == "easy") {
                trainingName.text = "Ходьба с пятки на носок"
                text1.text =
                    " 1. Пятку одной ноги ставим перед носком другой. Носок и пятка должны соприкасаться или почти соприкасаться"
                text2.text =
                    " 2. Вытяните руки в стороны, подтяните живот, спина прямая. Выберите точку перед собой и смотрите на нее, двигаясь по направлению к этой точке"
                text3.text =
                    " 3. Таким же образом меняйте ноги, делая шаги вперед. Сделайте 20 шагов"

                img1.setImageResource(R.drawable.steps1)
                img2.setImageResource(R.drawable.steps2)
                img3.setImageResource(R.drawable.steps3)
            }
            if (type == "balancing" && level == "norm") {
                trainingName.text = "Балансирование при ходьбе"
                text1.text = " 1. Вытяните руки в стороны, подтяните живот, спина прямая"
                text2.text =
                    " 2. Выберите точку перед собой и смотрите на нее, двигаясь по направлению к этой точке"
                text3.text =
                    " 3. Двигайтесь по прямой линии, поднимая одну ногу согнутую в колене, и переставляя ее вперед. Сделайте 20 шагов, меняя ноги"

                img1.setImageResource(R.drawable.walking1)
                img2.setImageResource(R.drawable.steps2)
                img3.setImageResource(R.drawable.steps3)
            }

            if (type == "balancing" && level == "hard") {
                trainingName.text = "Балансирование на одной ноге с опорой на стул"
                text1.text = " 1. Живот подтянут, спина прямая, подбородок приподнят, взгляд вперед"
                text2.text =
                    " 2. Стоя на одной ноге, держитесь за стул. Сохраняйте эту позицию 10—15 секунд"
                text3.text =
                    " 3. Повторите 10—15 раз на одной ноге, затем на другой. Если вы уверены в себе, можете закрыть глаза"

                img1.setImageResource(R.drawable.chair1)
                img2.setImageResource(R.drawable.chair2)
            }

            if (type == "flexibility" && level == "easy") {
                trainingName.text = "Растяжка квадрицепсов стоя"
                text1.text =
                    " 1. Сядьте на высокий стул и держите бедра, колени и пальцы ног обращенными вперед. Поднимите левую руку, правой держитесь за стул"
                text2.text =
                    " 2. Удерживая туловище вытянутым, медленно наклонитесь влево. Задержитесь в этом положении от 10 до 30 секунд, вернитесь в центр"
                text3.text = " 3. Повторите ту же растяжку с правой стороны"

                img1.setImageResource(R.drawable.flex_chair2)
            }

            if (type == "flexibility" && level == "norm") {
                trainingName.text = "Боковая растяжка над головой"
                text1.text = " 1. Живот подтянут, спина прямая, подбородок приподнят, взгляд вперед"
                text2.text =
                    " 2. Стоя на одной ноге, держитесь за стул, возьмите правой рукой правую нору. Сохраняйте эту позицию 10—15 секунд"
                text3.text = " 3. Повторите 5—10 раз на одной ноге, затем на другой"

                img1.setImageResource(R.drawable.flex_chair)
            }

            if (type == "flexibility" && level == "hard") {
                trainingName.text = "Выпад в кресле"
                text1.text =
                    " 1. Возьмите крепкий стул. Затем встаньте в полуметре от стула позади вас и положите голень на сиденье стула"
                text2.text =
                    " 2. Слегка согните переднее колено, толкая бедра вперед и вниз. Удерживайте это положение от 10 до 30 секунд"
                text3.text = " 3. Затем повторите с противоположной стороной"

                img1.setImageResource(R.drawable.hard_flex1)
                img2.setImageResource(R.drawable.hard_flex2)
            }

            if (type == "power" && level == "easy") {
                trainingName.text = "Работа с эспандером или мячом"
                text1.text = " 1. Возьмите в руку теннисный мяч или эспандер"
                text2.text = " 2. Медленно сжимайте мяч в руке на 3—5 секунд"
                text3.text = " 3. Медленно разожмите руку. Повторяйте по 10—15 раз каждой рукой"

                img1.setImageResource(R.drawable.ball1)
                img2.setImageResource(R.drawable.ball2)
                img3.setImageResource(R.drawable.ball3)
            }

            if (type == "power" && level == "norm") {
                trainingName.text = "Приседания со стулом"
                text1.text =
                    " 1. Сядьте на  стул, стопы плотно прилегают к  полу, выпрямите спину, сложите руки на груди, дыхание глубокое, медленное"
                text2.text =
                    " 2. Вытяните руки перед собой параллельно полу и на выдохе медленно поднимайтесь"
                text3.text = " 3. На вдохе сядьте обратно. Спина прямая.Повторите 10—15 раз"

                img1.setImageResource(R.drawable.power_chair1)
                img2.setImageResource(R.drawable.power_chair2)
                img3.setImageResource(R.drawable.power_chair3)
            }

            if (type == "power" && level == "hard") {
                trainingName.text = "Упражнения для рук"
                text1.text =
                    " 1. Упражнение можно выполнять как стоя, так и сидя на стуле. Ноги на полу, плечи расправлены. Возьмите в руки гантели или другие предметы весом 0,5—1,5кг"
                text2.text = " 2. Поднимите и согните руки в локтях 90°"
                text3.text =
                    " 3. На  выдохе вытяните руки вверх и  оставайтесь в такой позиции в течение секунды. На вдохе опустите руки. Повторяйте по 10—15 раз"

                img1.setImageResource(R.drawable.power_drumbbell1)
                img2.setImageResource(R.drawable.power_drumbbell2)
                img3.setImageResource(R.drawable.power_drumbbell3)
            }

            val calendar = Calendar.getInstance()
            val db = DBHelper(requireContext(), null)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH) + 1
            val year = calendar.get(Calendar.YEAR)

            if (!(db.checkDataForDay(day, month, year))) {
                db.addDataForDay(day, month, year)
            }

            val buttEnd: Button = dialogBinding.findViewById(R.id.end)
            buttEnd.setOnClickListener {
                db.changeTrainingsValue(day, month, year)
                db.changeTrainingsForMonthValue(month)
                myDialog.dismiss()
            }
        }

        val buttRate: Button = requireView().findViewById(R.id.rateHealth)
        buttRate.setOnClickListener {
            val dialogBinding = layoutInflater.inflate(R.layout.rate_health, null)
            val myDialog = Dialog(requireContext())
            myDialog.setContentView(dialogBinding)
            myDialog.setCancelable(true)
            myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            myDialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            myDialog.show()

            val questions = arrayOf(
                dialogBinding.findViewById<RadioGroup>(R.id.q1_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q2_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q3_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q4_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q5_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q6_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q7_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q8_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q9_radioGr),
                dialogBinding.findViewById<RadioGroup>(R.id.q10_radioGr)
            )

            val buttSave: Button = dialogBinding.findViewById(R.id.save_button)
            buttSave.setOnClickListener {
                val allAnswersSaved = (1..10).all { index ->
                    saveAnswer(questions[index - 1], index.toString(), dialogBinding)
                }
                if (allAnswersSaved) {
                    suggestWorkout()
                    myDialog.dismiss()
                } else {
                    Toast.makeText(
                        requireContext(), "Пожалуйста, ответьте на все вопросы",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            val buttTerminate: Button = dialogBinding.findViewById(R.id.terminate_button)
            buttTerminate.setOnClickListener {
                myDialog.dismiss()
            }
        }
    }

    private fun saveAnswer(question: RadioGroup, number: String, dialogBinding: View): Boolean {
        var selected: Boolean = true
        val selectedId: Int = question.checkedRadioButtonId
        if (selectedId != -1) {
            val selectedRadioButton: RadioButton = dialogBinding.findViewById(selectedId)
            val selectedValue = selectedRadioButton.text.toString()
            val sharedPrefs =
                requireContext().getSharedPreferences("HealthPrefs", Context.MODE_PRIVATE)
            with(sharedPrefs.edit()) {
                putString("q$number", selectedValue)
                apply()
            }
        } else {
            selected = false
        }
        return selected
    }

    private fun suggestWorkout() {
        val sharedPrefs = requireContext().getSharedPreferences("HealthPrefs", Context.MODE_PRIVATE)

        val hasDiseases = sharedPrefs.getString("q1", "Нет") == "Да"
        val wellRested = sharedPrefs.getString("q2", "Нет") == "Да"
        val lastMealTime = sharedPrefs.getString("q3", "") == "Менее 1 часа назад"
        val badMood = sharedPrefs.getString("q4", "Нейтральное") == "Чувствую себя подавленно..."
        val canJump = sharedPrefs.getString("q5", "Нет") == "Да"
        val canBend = sharedPrefs.getString("q6", "Нет") == "Да"
        val feelsTired = sharedPrefs.getString("q7", "Нет") == "Да"
        val breathingRate = sharedPrefs.getString("q8", "Нет") == "Да"
        val heartbeatRate = sharedPrefs.getString("q9", "Нет") == "Да"
        val pressure = sharedPrefs.getString("q10", "Умеренное") == "Умеренное"

        var workoutType = ""
        var workoutLevel = ""
        var msg = ""

        if (hasDiseases) {
            msg =
                "При наличии заболеваний рекомендуем обратиться к спецалисту перед началом тренировок"
        } else if (!pressure) {
            msg = "При повышенном/пониженном давлении рекомендуем воздержаться от тренировок"
        } else {
            if (feelsTired || lastMealTime || breathingRate || heartbeatRate) {
                workoutLevel = "Легкий"
            } else if (!wellRested || badMood) {
                workoutLevel = "Умеренный"
            } else {
                workoutLevel = "Сложный"
            }

            if (canJump && canBend) {
                workoutType = "Сила"
            } else if ((breathingRate || heartbeatRate) && canBend) {
                workoutType = "Гибкость"
            } else {
                workoutType = "Равновесие"
            }
        }

        showRecomendation(msg, workoutType, workoutLevel)
    }

    @SuppressLint("InflateParams")
    private fun showRecomendation(msg: String, type: String, level: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.recomendation, null)

        if (msg == "") {
            val typeLabel = layout.findViewById<TextView>(R.id.suggestedType)
            typeLabel.text = "Рекомендуемый тип тренировки:"
            val typeValue = layout.findViewById<TextView>(R.id.type)
            typeValue.text = type
            val levelLabel = layout.findViewById<TextView>(R.id.suggestedLevel)
            levelLabel.text = "Рекомендуемый уровень тренировки:"
            val levelValue = layout.findViewById<TextView>(R.id.level)
            levelValue.text = level
        } else {
            val typeLabel = layout.findViewById<TextView>(R.id.suggestedType)
            typeLabel.text = msg
        }

        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }
}
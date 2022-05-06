package com.rijaldev.myalarmmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rijaldev.myalarmmanager.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener,
    TimePickerFragment.DialogTimeListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmReceiver = AlarmReceiver()
        setUpView()
    }

    private fun setUpView() {
        with(binding) {
            btnOnceDate.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, DATE_PICKER_TAG)
            }
            btnOnceTime.setOnClickListener {
                val timePickerFragment = TimePickerFragment()
                timePickerFragment.show(supportFragmentManager, TIME_PICKER_ONCE_TAG)
            }
            btnSetOnceAlarm.setOnClickListener {
                val onceDate = tvOnceDate.text.toString()
                val onceTime = tvOnceTime.text.toString()
                val onceMessage = edtOnceMessage.text.toString()

                alarmReceiver.setOneTimeAlarm(
                    this@MainActivity,
                    AlarmReceiver.TYPE_ONE_TIME,
                    onceDate,
                    onceTime,
                    onceMessage
                )
            }
            btnRepeatingTime.setOnClickListener {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)

            }
            btnSetRepeatingAlarm.setOnClickListener {
                val repeatingTime = tvRepeatingTime.text.toString()
                val repeatMessage = edtRepeatingMessage.text.toString()
                alarmReceiver.setRepeatingAlarm(this@MainActivity,
                    AlarmReceiver.TYPE_REPEATING,
                    repeatingTime,
                    repeatMessage
                )
            }
            btnCancelRepeatingAlarm.setOnClickListener {
                alarmReceiver.cancelAlarm(this@MainActivity, AlarmReceiver.TYPE_REPEATING)
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        binding.tvOnceDate.text = dateFormat.format(calendar.time)
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_ONCE_TAG -> binding.tvOnceTime.text = dateFormat.format(calendar.time)
            TIME_PICKER_REPEAT_TAG -> binding.tvRepeatingTime.text = dateFormat.format(calendar.time)
            else -> {}
        }
    }

    companion object {
        private const val DATE_PICKER_TAG = "DatePicker"
        private const val TIME_PICKER_ONCE_TAG = "TimePickerOnce"
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }
}
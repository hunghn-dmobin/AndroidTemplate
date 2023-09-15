@file:Suppress("unused")

package appsgenz.template.utilities

import android.util.Log
import appsgenz.template.BuildConfig

/**
 * @author HungHN on 3/15/2018.
 */
object AppLog {

    fun d(message: String) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.d(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message)
        }
    }

    fun d(message: String, error: Exception) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.d(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message, error)
        }
    }

    fun e(message: String) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.e(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message)
        }
    }

    fun e(message: String, error: Exception) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.e(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message, error)
        }
    }

    fun i(message: String) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.i(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message)
        }
    }

    fun i(message: String, error: Exception) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.i(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message, error)
        }
    }

    fun w(message: String) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.w(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message)
        }
    }

    fun w(message: String, error: Exception) {
        val stackTraceElement = Throwable().stackTrace[1]
        if (BuildConfig.DEBUG) {
            Log.w(stackTraceElement.fileName + " in " + stackTraceElement.methodName +
                    " at line " + stackTraceElement.lineNumber, message, error)
        }
    }
}

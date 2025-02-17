package com.global.bean

import java.nio.file.Path

object DBConfig {
    private var mode = "dev"

    fun setModeToTest() {
        mode = "test"
    }

    fun setModeToDev() {
        mode = "dev"
    }

    val dbDirPath: Path
        get() {
            return Path.of("db/${mode}")
        }
}
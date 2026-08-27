fun main() {
    println("Bienvenido al sistema de ubicación para zonas públicas WIFIIII")

    val usuario = "51593"
    val contrasena = "39515"

    println("Ingrese su usuario: ")
    val entradaUsuario = readln()

    if (entradaUsuario != usuario) {
        println("ERROR")
        return
    }

    println("Ingrese su contrasena: ")
    val contrasenaEntrada = readln()

    if (contrasenaEntrada != contrasena) {
        println("ERROR")
        return
    }

    val termino1 = 593
    val termino2 = (3 % 5) + 5 + 1
    val resultado = termino1 + termino2

    println("Ingrese el resultado: ")
    println("$termino1 + $termino2 =")

    val resultadoEntrada = readln().toInt()

    if (resultadoEntrada != resultado) {
        println("ERROR")
        return
    }

    println("Sesión iniciada")

    val menu = mutableListOf(
        "Cambiar contraseña",
        "Ingresar coordenadas actuales",
        "Ubicar zona wifi más cercana",
        "Guardar archivo con ubicación cercana",
        "Actualizar registros de zonas wifi desde archivo"
    )

    var intentosFallidos = 0

    while (true) {
        println("1. ${menu[0]}")
        println("2. ${menu[1]}")
        println("3. ${menu[2]}")
        println("4. ${menu[3]}")
        println("5. ${menu[4]}")
        println("6. Elegir opción de menú favorita")
        println("7. Cerrar sesión")
        println("Elija una opción")

        val opcionElegida = readln().toInt()

        when (opcionElegida) {
            1, 2, 3, 4, 5 -> println("Usted ha elegido la opción número $opcionElegida")

            6 -> {
                println("Seleccione opción favorita")
                val favorita = readln().toInt()

                if (favorita < 1 || favorita > 5) {
                    println("Error")
                    return
                }

                println("Si me giras pierdo tres unidades por eso debes colocarme siempre de pie, la respuesta es:")
                val respuesta1 = readln().toInt()

                if (respuesta1 != 9) {
                    println("Error")
                    return
                }

                println("Me separaron de mi hermano siamés, antes era un ocho y ahora soy un…, la respuesta es:")
                val respuesta2 = readln().toInt()

                if (respuesta2 != 3) {
                    println("Error")
                    return
                }

                val elegida = menu[favorita - 1]
                menu.removeAt(favorita - 1)
                menu.add(0, elegida)
                // el while vuelve a repetir y muestra el menú ya actualizado
            }

            7 -> {
                println("Hasta pronto")
                return
            }

            else -> {
                intentosFallidos++
                println("Error")
                if (intentosFallidos >= 3) {
                    return
                }
            }
        }
    }
}
fun main() {
    println("Bienvenido al sistema de ubicación para zonas públicas WIFIIII")

    val usuario = "51593"
    var contrasena = "39515" // ahora es "var" porque el RF01 permite cambiarla

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

    // ---------------------------------------------------------
    // RETO 3: variables de apoyo
    // ---------------------------------------------------------
    // Rangos de validación de coordenadas según el PENÚLTIMO dígito
    // del código de grupo (51593 -> 9 -> Curití, Santander)
    val latSup = 6.690
    val latInf = 6.532
    val lonOr = -72.872   // límite oriente (menos negativo)
    val lonOcc = -73.120  // límite occidente (más negativo)

    // Matriz de 3 filas x 2 columnas: [fila][0]=latitud, [fila][1]=longitud
    val coordenadas = Array(3) { DoubleArray(2) }
    var coordenadasCargadas = false

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

            1 -> {
                // RF01: cambiar contraseña
                println("Ingrese la contraseña actual: ")
                val actualIngresada = readln()

                if (actualIngresada != contrasena) {
                    println("Error")
                    return
                }

                println("Ingrese la nueva contraseña: ")
                val nueva = readln()

                // la nueva contraseña no puede ser igual a la actual
                if (nueva == contrasena) {
                    println("Error")
                    return
                }

                contrasena = nueva
                println("Contraseña actualizada correctamente")
                // el programa vuelve al menú principal (continúa el while)
            }

            2 -> {
                if (!coordenadasCargadas) {
                    // RF02: primera vez que ingresa las 3 coordenadas
                    for (i in 0..2) {
                        println("Ingrese la latitud del punto ${i + 1}: ")
                        val latTexto = readln()
                        println("Ingrese la longitud del punto ${i + 1}: ")
                        val lonTexto = readln()

                        // si deja alguna coordenada vacía, termina con error
                        if (latTexto.isBlank() || lonTexto.isBlank()) {
                            println("Error")
                            return
                        }

                        val lat = latTexto.toDouble()
                        val lon = lonTexto.toDouble()

                        // validación contra los rangos permitidos
                        if (lat < latInf || lat > latSup || lon < lonOcc || lon > lonOr) {
                            println("Error coordenada")
                            return
                        }

                        coordenadas[i][0] = lat
                        coordenadas[i][1] = lon
                    }

                    coordenadasCargadas = true
                    println("Coordenadas guardadas correctamente")
                    // regresa al menú principal
                } else {
                    // RF03: ya hay coordenadas guardadas -> actualizar
                    for (i in 0..2) {
                        val latTxt = "%.3f".format(coordenadas[i][0])
                        val lonTxt = "%.3f".format(coordenadas[i][1])
                        println("coordenada [latitud,longitud] ${i + 1} : ['$latTxt', '$lonTxt']")
                    }

                    // índice de la coordenada más al norte (mayor latitud)
                    var indiceNorte = 0
                    for (i in 1..2) {
                        if (coordenadas[i][0] > coordenadas[indiceNorte][0]) indiceNorte = i
                    }

                    // coordenada promedio de los 3 puntos
                    val promedioLat = (coordenadas[0][0] + coordenadas[1][0] + coordenadas[2][0]) / 3
                    val promedioLon = (coordenadas[0][1] + coordenadas[1][1] + coordenadas[2][1]) / 3
                    val promLatTxt = "%.3f".format(promedioLat)
                    val promLonTxt = "%.3f".format(promedioLon)

                    // Mensajes clave según el ÚLTIMO dígito del código de grupo (51593 -> 3):
                    // "más al norte" y "promedio de todos los puntos"
                    println("La coordenada ${indiceNorte + 1} es la que está más al norte")
                    println("La coordenada promedio es: ['$promLatTxt', '$promLonTxt']")

                    println("Presione 1,2 o 3 para actualizar la respectiva coordenada. Presione 0 para regresar al menú")
                    val opcionActualizar = readln().toInt()

                    when {
                        opcionActualizar == 0 -> {
                            // regresa al menú principal sin cambios
                        }
                        opcionActualizar in 1..3 -> {
                            println("Ingrese la nueva latitud: ")
                            val nuevaLat = readln().toDouble()
                            println("Ingrese la nueva longitud: ")
                            val nuevaLon = readln().toDouble()

                            if (nuevaLat < latInf || nuevaLat > latSup || nuevaLon < lonOcc || nuevaLon > lonOr) {
                                println("Error coordenada")
                                return
                            }

                            coordenadas[opcionActualizar - 1][0] = nuevaLat
                            coordenadas[opcionActualizar - 1][1] = nuevaLon
                            println("Coordenada actualizada correctamente")
                            // regresa al menú principal
                        }
                        else -> {
                            println("Error actualización")
                            return
                        }
                    }
                }
            }

            3, 4, 5 -> println("Usted ha elegido la opción número $opcionElegida")

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
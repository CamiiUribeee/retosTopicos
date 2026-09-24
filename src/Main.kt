import kotlin.math.*

fun main() {

    //pido credenciales para iniciar
    println("Bienvenido al sistema de ubicación para zonas públicas WIFI")

    val usuario = "51593"
    var contrasena = "39515" //"var" para cambiarla más adelante

    println("Ingrese su usuario: ")
    val entradaUsuario = readln()

    if (entradaUsuario != usuario) {
        println("Error")
        return
    }

    println("Ingrese su contrasena: ")
    val contrasenaEntrada = readln()

    if (contrasenaEntrada != contrasena) {
        println("Error")
        return
    }

    // captcha de seguridad
    val termino1 = 593
    val termino2 = (3 % 5) + 5 + 1
    val resultadoCorrecto = termino1 + termino2

    println("$termino1 + $termino2 =")
    val resultadoEntrada = readln().toInt()

    if (resultadoEntrada != resultadoCorrecto) {
        println("Error")
        return
    }

    println("Sesión iniciada")

    // rango de validación según el penúltimo dígito

    val latSup = 6.690
    val latInf = 6.532
    val lonOr = -72.872   // límite oriente
    val lonOcc = -73.120  // límite occidente

    // para no repetir la misma validación en varias partes
    fun coordenadaValida(lat: Double, lon: Double): Boolean {
        return lat in latInf..latSup && lon in lonOcc..lonOr
    }

    val coordenadas = Array(3) { DoubleArray(2) }
    var coordenadasCargadas = false

    val zonasWifi = arrayOf(
        doubleArrayOf(6.632, -72.984, 285.0),
        doubleArrayOf(6.564, -73.061, 127.0),
        doubleArrayOf(6.531, -73.002, 15.0),
        doubleArrayOf(6.623, -72.978, 56.0)
    )

    for (i in 0..3) {
        if (!coordenadaValida(zonasWifi[i][0], zonasWifi[i][1])) {
            println("Aviso: la zona wifi ${i + 1} está fuera de los límites del reto 3 (revisar dato de la tabla anexa)")
        }
    }


    // calcular la distancia (en metros)

    fun calcularDistanciaMetros(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val radioTierraKm = 6372.795477598

        val lat1Rad = Math.toRadians(lat1)
        val lat2Rad = Math.toRadians(lat2)
        val deltaLatRad = Math.toRadians(lat2 - lat1)
        val deltaLonRad = Math.toRadians(lon2 - lon1)

        val a = sin(deltaLatRad / 2).pow(2) +
                cos(lat1Rad) * cos(lat2Rad) * sin(deltaLonRad / 2).pow(2)

        val distanciaKm = 2 * radioTierraKm * asin(sqrt(a))

        // la fórmula da el resultado en kilómetros y lo paso a metros
        return distanciaKm * 1000
    }

    val menu = mutableListOf(
        "Cambiar contraseña",
        "Ingresar coordenadas actuales",
        "Ubicar zona wifi más cercana",
        "Guardar archivo con ubicación cercana",
        "Actualizar registros de zonas wifi desde archivo"
    )

    // contador de intentos fallidos
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


        if (opcionElegida in 1..7) {
            intentosFallidos = 0
        }

        when (opcionElegida) {

            1 -> {
                // para cambiar contraseña
                println("Ingrese la contraseña actual: ")
                val actualIngresada = readln()

                if (actualIngresada != contrasena) {
                    println("Error")
                    return
                }

                println("Ingrese la nueva contraseña: ")
                val nueva = readln()


                if (nueva == contrasena) {
                    println("Error")
                    return
                }

                contrasena = nueva
                println("Contraseña actualizada correctamente")

            }

            2 -> {
                if (!coordenadasCargadas) {

                    for (i in 0..2) {
                        println("Ingrese la latitud del punto ${i + 1}: ")
                        val latTexto = readln()
                        println("Ingrese la longitud del punto ${i + 1}: ")
                        val lonTexto = readln()

                        if (latTexto.isBlank() || lonTexto.isBlank()) {
                            println("Error")
                            return
                        }

                        val lat = latTexto.toDouble()
                        val lon = lonTexto.toDouble()

                        if (!coordenadaValida(lat, lon)) {
                            println("Error coordenada")
                            return
                        }

                        coordenadas[i][0] = lat
                        coordenadas[i][1] = lon
                    }

                    coordenadasCargadas = true
                    println("Coordenadas guardadas correctamente")

                } else {
                    for (i in 0..2) {
                        val latTxt = "%.3f".format(coordenadas[i][0])
                        val lonTxt = "%.3f".format(coordenadas[i][1])
                        println("coordenada [latitud,longitud] ${i + 1} : ['$latTxt', '$lonTxt']")
                    }

                    var indiceNorte = 0
                    for (i in 1..2) {
                        if (coordenadas[i][0] > coordenadas[indiceNorte][0]) indiceNorte = i
                    }

                    // coordenada promedio de los 3 puntos
                    val promedioLat = (coordenadas[0][0] + coordenadas[1][0] + coordenadas[2][0]) / 3
                    val promedioLon = (coordenadas[0][1] + coordenadas[1][1] + coordenadas[2][1]) / 3
                    val promLatTxt = "%.3f".format(promedioLat)
                    val promLonTxt = "%.3f".format(promedioLon)

                    println("La coordenada ${indiceNorte + 1} es la que está más al norte")
                    println("La coordenada promedio es: ['$promLatTxt', '$promLonTxt']")

                    println("Presione 1,2 o 3 para actualizar la respectiva coordenada. Presione 0 para regresar al menú")
                    val opcionActualizar = readln().toInt()

                    when {
                        opcionActualizar == 0 -> {
                            // regresa sin cambios
                        }
                        opcionActualizar in 1..3 -> {
                            println("Ingrese la nueva latitud: ")
                            val nuevaLat = readln().toDouble()
                            println("Ingrese la nueva longitud: ")
                            val nuevaLon = readln().toDouble()

                            if (!coordenadaValida(nuevaLat, nuevaLon)) {
                                println("Error coordenada")
                                return
                            }

                            coordenadas[opcionActualizar - 1][0] = nuevaLat
                            coordenadas[opcionActualizar - 1][1] = nuevaLon
                            println("Coordenada actualizada correctamente")
                        }
                        else -> {
                            println("Error actualización")
                            return
                        }
                    }
                }
            }

            3 -> {

                // RETO 4 - zona wifi más cercana

                if (!coordenadasCargadas) {
                    println("Error sin registro de coordenadas")
                    return
                }

                for (i in 0..2) {
                    val latTxt = "%.3f".format(coordenadas[i][0])
                    val lonTxt = "%.3f".format(coordenadas[i][1])
                    println("coordenada [latitud,longitud] ${i + 1} : ['$latTxt', '$lonTxt']")
                }

                println("Por favor elija su ubicación actual (1,2 ó 3) para calcular la distancia a los puntos de conexión")
                val ubicacionElegida = readln().toInt()

                if (ubicacionElegida < 1 || ubicacionElegida > 3) {
                    println("Error ubicación")
                    return
                }

                val latUsuario = coordenadas[ubicacionElegida - 1][0]
                val lonUsuario = coordenadas[ubicacionElegida - 1][1]

                // calculo la distancia desde la ubicación elegida
                val distancias = DoubleArray(4)
                for (i in 0..3) {
                    distancias[i] = calcularDistanciaMetros(
                        latUsuario, lonUsuario,
                        zonasWifi[i][0], zonasWifi[i][1]
                    )
                }

                var indiceMasCercana = 0
                for (i in 1..3) {
                    if (distancias[i] < distancias[indiceMasCercana]) {
                        indiceMasCercana = i
                    }
                }

                var indiceSegundaCercana = -1
                for (i in 0..3) {
                    if (i != indiceMasCercana) {
                        if (indiceSegundaCercana == -1 || distancias[i] < distancias[indiceSegundaCercana]) {
                            indiceSegundaCercana = i
                        }
                    }
                }

                var indiceMenosUsuarios = indiceMasCercana
                var indiceMasUsuarios = indiceSegundaCercana
                if (zonasWifi[indiceSegundaCercana][2] < zonasWifi[indiceMasCercana][2]) {
                    indiceMenosUsuarios = indiceSegundaCercana
                    indiceMasUsuarios = indiceMasCercana
                }

                println("Zonas wifi cercanas con menos usuarios")

                val lat1Txt = "%.3f".format(zonasWifi[indiceMenosUsuarios][0])
                val lon1Txt = "%.3f".format(zonasWifi[indiceMenosUsuarios][1])
                val dist1Txt = "%.0f".format(distancias[indiceMenosUsuarios])
                val usu1 = zonasWifi[indiceMenosUsuarios][2].toInt()
                println("La zona wifi 1: ubicada en ['$lat1Txt','$lon1Txt'] a $dist1Txt metros , tiene en promedio $usu1 usuarios")

                val lat2Txt = "%.3f".format(zonasWifi[indiceMasUsuarios][0])
                val lon2Txt = "%.3f".format(zonasWifi[indiceMasUsuarios][1])
                val dist2Txt = "%.0f".format(distancias[indiceMasUsuarios])
                val usu2 = zonasWifi[indiceMasUsuarios][2].toInt()
                println("La zona wifi 2: ubicada en ['$lat2Txt','$lon2Txt'] a $dist2Txt metros , tiene en promedio $usu2 usuarios")

                println("Elija 1 o 2 para recibir indicaciones de llegada")
                val zonaElegida = readln().toInt()

                if (zonaElegida != 1 && zonaElegida != 2) {
                    println("Error zona wifi")
                    return
                }

                val indiceZonaFinal = if (zonaElegida == 1) indiceMenosUsuarios else indiceMasUsuarios
                val distanciaFinal = distancias[indiceZonaFinal]

                val deltaLat = zonasWifi[indiceZonaFinal][0] - latUsuario
                val deltaLon = zonasWifi[indiceZonaFinal][1] - lonUsuario

                val direccionVertical = if (deltaLat >= 0) "norte" else "sur"
                val direccionHorizontal = if (deltaLon >= 0) "oriente" else "occidente"

                println("Para llegar a la zona wifi dirigirse primero al $direccionHorizontal y luego hacia el $direccionVertical")

                val velocidadBusMS = 16.67
                val velocidadBicicletaMS = 3.33

                val tiempoBusMin = (distanciaFinal / velocidadBusMS) / 60
                val tiempoBicicletaMin = (distanciaFinal / velocidadBicicletaMS) / 60

                println("Tiempo en bus: ${"%.1f".format(tiempoBusMin)} minutos")
                println("Tiempo en bicicleta: ${"%.1f".format(tiempoBicicletaMin)} minutos")

                //OJO
                println("Presione 0 para salir")
                val salir = readln().toInt()

                if (salir != 0) {
                    println("Error zona wifi")
                    return
                }
            }

            4, 5 -> {

                println("Usted ha elegido la opción número $opcionElegida")
                return
            }

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
                    // si falla la primera adivinanza, no se hace el cambio
                    println("Error")
                } else {
                    println("Me separaron de mi hermano siamés, antes era un ocho y ahora soy un…, la respuesta es:")
                    val respuesta2 = readln().toInt()

                    if (respuesta2 != 3) {

                        println("Error")
                    } else {

                        val elegida = menu[favorita - 1]
                        menu.removeAt(favorita - 1)
                        menu.add(0, elegida)
                        println("Opción favorita actualizada correctamente")
                    }
                }

            }

            7 -> {
                println("Hasta pronto")
                return
            }

            else -> {
                intentosFallidos++
                println("Error")
                if (intentosFallidos > 3) {
                    return
                }
                // si aún no llega a 3 fallos seguidos, se vuelve a pedir la opción
            }
        }
    }
}
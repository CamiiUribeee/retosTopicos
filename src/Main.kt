//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("Bienvenido al sistema de ubicación para zonas públicas WIFI")

    val usuario="51593"

    val contrasena="39515"

    println("Ingrese su usuario: ")
    val entradaUsuario=readln()

    if(entradaUsuario!=usuario){
        println("ERROR")
        return
    }

    println("Ingrese su contrasena: ")
    val contrasenaEntrada=readln()

    if(contrasenaEntrada!=contrasena){
        println("ERROR")
        return
    }

    val termino1=593
    val termino2=(3%5)+5+1
    val resultado=termino1+termino2


    println("Ingrese el resultado: ")
    println("$termino1 + $termino2 =")

    val resultadoEntrada=readln().toInt()

    if(resultadoEntrada!=resultado){
        println("ERROR")
        return
    }

    println("Sesión iniciada")

    println("Menú de opciones desbloqueado: ")

    val menu=mutableListOf(

        "Cambiar contraseña",
        "Ingresar coordenadas actuales",
        "Ubicar zona wifi más cercana",
        "Guardar archivo con ubicación cercana",
        "Actualizar registros de zonas wifi desde archivo"


    )
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
        1,2,3,4,5 -> println("Usted ha elegido la opción número $opcionElegida")

        7-> {
            println("Hasta pronto")
        } else -> {
            println("ERROR")
        }
    }




}
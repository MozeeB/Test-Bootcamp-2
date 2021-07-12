//PALINDROME

fun isPalindromeString(inputStr: String): Boolean {
    val sb = StringBuilder(inputStr)
    val reverseStr = sb.reverse().toString()

    return inputStr.equals(reverseStr, ignoreCase = true)
}

fun main() {

    val inString: String = "Radar"
    println("Enter String : ${inString}")

    if (isPalindromeString(inString)) {
        println(true)
    } else {
        println(false)
    }
}

//Leap Year

fun main() {
    
    val start = 1900
    val end = 2020
    
   	var leap = false

    for(value in start..end){

    if (value % 4 == 0) {
        if (value % 100 == 0) {
            leap = value % 400 == 0
        } else
            leap = true
    } else 
        leap = false

    if(leap){
       println("$value") 
    }
	}
}

//Reverese Word
fun main() {
    
    var word = "I am A Great human"
    print(reverseWord(word))
   
}

fun reverseWord(str: String): String {
    
 val words = str.split(" ".toRegex()).dropLastWhile { 
     it.isEmpty() 
 }.toTypedArray() 
    
  var reversedString = ""
    
  for (i in words.indices) {
    val word = words[i]
    var reverseWord = ""
      
    for (j in word.length - 1 downTo 0) {
      reverseWord = reverseWord + word[j]
    }
    reversedString = "$reversedString$reverseWord "
  }
  return reversedString
}

//Nearest Fibonacci

val n = 10
    var t1 = 0
    var t2 = 1
    
    print("First $n : ")

    for (i in 1..n) {
        print("$t1 + ")

        val sum = t1 + t2
        t1 = t2
        t2 = sum
    }


//FizzBuzz
fun main() {
    
   	val n = 30
    val temp = arrayListOf<String>()
    for(x in 1..n){
        if (x%3 == 0 && x%5 == 0){
            temp.add("FizzBuzz")
        }else if(x%3 == 0){
            temp.add("Fizz")
        }else if(x%5 == 0){
            temp.add("Buzz")
        }else{
            temp.add(x.toString())
        }
    }
    print(temp)
   
}





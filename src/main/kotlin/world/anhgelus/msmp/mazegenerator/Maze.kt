package world.anhgelus.msmp.mazegenerator

import world.anhgelus.msmp.mazegenerator.area.MazeArea
import world.anhgelus.msmp.mazegenerator.area.MazePatterns
import java.util.*

/**
 * Maze reference class
 *
 * Available at https://github.com/JosherLo/Maze
 *
 * Distributed under no license
 *
 * @author JosherLo and MSMP (Anhgelus Morhtuuzh)
 */
class Maze(val patterns: MazePatterns, val area: MazeArea) {

    private val walls: MutableList<Int> = ArrayList()
    private val squares = HashMap<Int, MutableList<Int>>()
    private val squaresOriginal = HashMap<Int, MutableList<Int>>()
    private val n: Int = area.n
    private val m: Int = area.m

    fun getWallPosition(): List<Int> {
        initSquares()
        return if (n < 2 || m < 2) {
            ArrayList<Int>()
        } else {
            createMaze()
            var lst: MutableList<Int> = ArrayList()
            for (i in squares.keys) {
                lst = squares[i]!!
                break
            }
            if (lst.size >= n * m / 500 + 1 + 2 * (n + m) && m * n / 500 > 0) {
                val times: Int = Random().nextInt(n * m / 500) + 1 + (n + m)
                for (i in 0 until times) {
                    lst.remove(Random().nextInt(lst.size - 1))
                }
            }
            lst
        }
    }

    private fun initSquares() {
        for (i in 1 until 2 * n * m - n - m + 1) {
            walls.add(i)
        }
        for (i in 1 until n * m + 1) {
            squares[i] = ArrayList()
            squaresOriginal[i] = ArrayList()
        }
        for (i in walls) {
            if (i < n * m - m + 1) { //Vertical
                squares[i + (i - 1) / (n - 1)]!!.add(i)
                squares[i + 1 + (i - 1) / (n - 1)]!!.add(i)
                squaresOriginal[i + (i - 1) / (n - 1)]!!.add(i)
                squaresOriginal[i + 1 + (i - 1) / (n - 1)]!!.add(i)
            } else { //Horizontal
                squares[i - n * m + m]!!.add(i)
                squares[i - n * m + n + m]!!.add(i)
                squaresOriginal[i - n * m + m]!!.add(i)
                squaresOriginal[i - n * m + n + m]!!.add(i)
            }
        }
    }

    fun getSquares(): HashMap<Int, MutableList<Int>> {
        return squaresOriginal
    }

    private fun getSquaresList(num: Int): List<Int> {
        val lst: MutableList<Int> = ArrayList()
        for (i in squares.keys) {
            if (squares[i]!!.contains(num)) {
                lst.add(i)
            } else continue
            if (lst.size == 2) {
                break
            }
        }
        return lst
    }

    private fun makeSame(lst: List<Int>, num: Int) {
        for (i in squares[lst[0]]!!) {
            if (!squares[lst[1]]!!.contains(i)) {
                squares[lst[1]]!!.add(i)
            }
        }
        squares[lst[1]]!!.removeAt(squares[lst[1]]!!.indexOf(num))
        squares[lst[1]]!!.sort()
        squares.remove(lst[0])
    }

    private fun createMaze() {
        var keys: MutableList<Int> = ArrayList(squares.keys)
        keys.shuffle()
        val random = Random()
        while (squares.size != 1) {
            for (i in keys) {
                try {
                    val num = squares[i]!![random.nextInt(squares[i]!!.size - 1)]
                    val lst = getSquaresList(num)
                    if (squares[lst[0]] != squares[lst[1]] || squares.size == 2 && squares[lst[0]] == squares[lst[1]]) {
                        makeSame(lst, num)
                    }
                    keys = ArrayList(squares.keys)
                } catch (ignored: Exception) {
                }
            }
        }
    }
}
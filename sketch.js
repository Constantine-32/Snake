'use strict';

class Cell {
  constructor(x, y) {
    this.x = x
    this.y = y
    this.w = [true, true, true, true]
  }

  moveW() {
    this.y--
    if (this.y < 0) this.y = row - 1
  }

  moveA() {
    this.x--
    if (this.x < 0) this.x = col - 1
  }

  moveS() {
    this.y++
    if (this.y >= row) this.y = 0
  }

  moveD() {
    this.x++
    if (this.x >= col) this.x = 0
  }

  hiddeTop() {
    this.w[0] = false
  }

  hiddeRig() {
    this.w[1] = false
  }

  hiddeBot() {
    this.w[2] = false
  }

  hiddeLef() {
    this.w[3] = false
  }

  resetWalls() {
    this.w = [true, true, true, true]
  }

  draw() {
    let x0 = this.x * dim
    let x1 = x0 + dim
    let y0 = this.y * dim
    let y1 = y0 + dim

    noStroke()
    fill('#27ae60')
    rect(x0, y0, dim, dim)
    stroke('#111')

    if (this.w[0]) line(x0, y0, x1, y0)
    if (this.w[1]) line(x1, y0, x1, y1)
    if (this.w[2]) line(x1, y1, x0, y1)
    if (this.w[3]) line(x0, y1, x0, y0)
  }
}

class Snake {
  constructor() {
    this.spawn()
  }

  spawn() {
    this.body = [new Cell(1, 1)]
  }

  get x() {
    return this.body[0].x
  }

  get y() {
    return this.body[0].y
  }

  get length() {
    return this.body.length
  }

  colision() {
    for (let i = 1; i < this.length; i++)
      if (this.x === this.body[i].x && this.y === this.body[i].y)
        return true
    return false
  }

  isOnTop(x, y) {
    for (let cell of this.body)
      if (cell.x === x && cell.y === y)
        return true
    return false
  }

  eat() {
    for (let i = 0; i < 5; i++)
      this.body.push(new Cell(this.body[this.length - 1].x, this.body[this.length - 1].y))
  }

  updateTail() {
    for (let i = this.length - 1; i > 0; i--) {
      this.body[i].x = this.body[i - 1].x
      this.body[i].y = this.body[i - 1].y
    }
  }

  moveW() {
    this.updateTail()
    this.body[0].moveW()
  }

  moveA() {
    this.updateTail()
    this.body[0].moveA()
  }

  moveS() {
    this.updateTail()
    this.body[0].moveS()
  }

  moveD() {
    this.updateTail()
    this.body[0].moveD()
  }

  updateWalls() {
    for (let cell of this.body) cell.resetWalls()
    for (let i = 0; i < this.length - 1; i++) {
      let x = this.body[i].x - this.body[i + 1].x
      if (x === 1) {
        this.body[i].hiddeLef()
        this.body[i + 1].hiddeRig()
      } else if (x === -1) {
        this.body[i].hiddeRig()
        this.body[i + 1].hiddeLef()
      }
      let y = this.body[i].y - this.body[i + 1].y
      if (y === 1) {
        this.body[i].hiddeTop()
        this.body[i + 1].hiddeBot()
      } else if (y === -1) {
        this.body[i].hiddeBot()
        this.body[i + 1].hiddeTop()
      }
      if (x === 0 && y === 0) {
        this.body[i + 1].hiddeRig()
        this.body[i + 1].hiddeLef()
        this.body[i + 1].hiddeBot()
        this.body[i + 1].hiddeTop()
      }
    }
  }

  draw() {
    this.updateWalls()
    background('#111')
    for (let cell of this.body)
      cell.draw()
  }
}

class Apple {
  constructor() {
    this.spawn()
  }

  spawn() {
    this.x = Math.floor(Math.random() * col)
    this.y = Math.floor(Math.random() * row)
  }

  draw() {
    stroke('#111')
    fill('#c0392b')
    rect(this.x * dim, this.y * dim,  dim,  dim)
  }
}

let cnv

const row = 40
const col = 40
const dim = 10
const hei = row * dim + 1
const wid = col * dim + 1

let game = true
let ticks = 0
let snake = new Snake()
let apple = new Apple()
let keych = ''

function centerCanvas() {
  let x = (windowWidth - width) / 2
  let y = (windowHeight - height) / 2
  cnv.position(x, y)
}

function setup() {
  cnv = createCanvas(wid, hei)
  centerCanvas()
  frameRate(60)
  background(255, 0, 200)
  snake.draw()
  apple.draw()
}

function windowResized() {
  centerCanvas()
}

function draw() {
  if (game && ticks === 5) {
    if ((snake.length === 1 || keych !== 's') && (key === 'w' || key === '&')) keych = 'w'
    if ((snake.length === 1 || keych !== 'd') && (key === 'a' || key === '%')) keych = 'a'
    if ((snake.length === 1 || keych !== 'w') && (key === 's' || key === '(')) keych = 's'
    if ((snake.length === 1 || keych !== 'a') && (key === 'd' || key === "'")) keych = 'd'
    if (keych === 'w') snake.moveW()
    if (keych === 'a') snake.moveA()
    if (keych === 's') snake.moveS()
    if (keych === 'd') snake.moveD()
    if (snake.colision()) game = false
    if (game) {
      if (snake.x === apple.x && snake.y === apple.y) {
        snake.eat()
        do { apple.spawn() } while (snake.isOnTop(apple.x, apple.y))
      }
      snake.draw()
      apple.draw()
      gameInfoText()
    } else gameOverText()
    ticks = 0
  }
  ticks++
}

function keyPressed() {
  if (key === 'R') {
    snake.spawn()
    apple.spawn()
    keych = ''
    ticks = 0
    game = true
  }
}

function gameInfoText() {
  textAlign(LEFT)
  fill(255, 255, 255)
  textSize(12)
  // text('Apple coords: ' + apple.x + ', ' + apple.y, 4, 16)
  // text('Snake coords: ' + snake.x + ', ' + snake.y, 4, 32)
  text('Snake length: ' + snake.length, 294, 390)
}

function gameOverText() {
  textAlign(CENTER)
  fill(255, 255, 255)
  textSize(42)
  text('Game Over!', 200, 200)
  textSize(18)
  text('(Press \'R\' to restart)', 200, 220)
}
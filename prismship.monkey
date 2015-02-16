Import Mojo

Const TILE_WIDTH:Int = 48
Const TILE_HEIGHT:Int = 48

Const SCREEN_WIDTH:Int = 640
Const SCREEN_HEIGHT:Int = 480

Const PLAYER_WIDTH:Int = 24
Const PLAYER_HEIGHT:Int = 48

Const BULLET_WIDTH:Int = 4
Const BULLET_HEIGHT:Int = 16

Const ENEMY_WIDTH:Int = 32
Const ENEMY_HEIGHT:Int = 32

Const USE_KEYBOARD:Int = 0
Const USE_TOUCH:Int = 1


' class to handle points and velocity in 2d space
Class Vec2D
	Field x:Float
	Field y:Float
	
	Method New(x:Float=0, y:Float=0)
		Set(x,y)
	End
	
	Method Set(x:Float, y:Float)
		Self.x = x
		Self.y = y
	End
End

' class to handle player ship
Class Player
	Field position:Vec2D
	Field velocity:Vec2D
	Field originalPosition:Vec2D
	
	Field speed:Float = 3.0
	Field friction:Float = 0.3
	Field colors:Int[] = [0, 0, 0]
	Field controls:Int
	Field lastBullet:Float
	Field fireRate:Float
	Field bullet_speed:Float = -8.0
	
	Field playerImages:Image[]
	Field currentImg:Int = 0
	
	Field leftKey:Int = KEY_A
	Field rightKey:Int = KEY_D
	Field upKey:Int = KEY_W
	Field downKey:Int = KEY_S
	Field fireKey:Int = KEY_SPACE
	
	Field topLeft:Vec2D = New Vec2D()
	Field topRight:Vec2D = New Vec2D()
	Field botLeft:Vec2D = New Vec2D()
	Field botRight:Vec2D = New Vec2D()
	Field box:CollisionRect
	
	
	Method New(x:Float, y:Float, color:Float, controls:Int=0, rate:Float, imgArray:Image[], moveSpeed:Float=3.0)
		position = New Vec2D(x, y)
		originalPosition = New Vec2D(x, y)
		velocity = New Vec2D()
		box = New CollisionRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT)
		fireRate = rate
		lastBullet = Millisecs()
		speed = moveSpeed
		playerImages = imgArray
		
		If color < 1.0
			colors[0] = 255
			currentImg = 0
		Else If color < 2.0 And color >= 1.0
			colors[1] = 255
			currentImg = 1
		Else If color < 3.0 And color >= 2.0
			colors[2] = 255
			currentImg = 2
		Else
			colors = [128, 128, 128]
		End
		
	End
	
	Method Draw()
		SetColor(colors[0], colors[1], colors[2])
		DrawImage(playerImages[currentImg], position.x, position.y)
	End
	
	Method Reset()
		SetPosition(originalPosition.x, originalPosition.y)
		velocity.Set(0, 0)
	End
	
	Method Update()
		' Use friction for smooth movement
		If velocity.x > friction
			velocity.x -= friction
		Else If velocity.x < -friction
			velocity.x += friction
		Else
			velocity.x = 0
		End
		
		If velocity.y > 1.0
			velocity.y -= friction
		Else If velocity.y < -1.0
			velocity.y += friction
		Else
			velocity.y = 0
		End
		
		If controls = USE_KEYBOARD
			If KeyDown(leftKey) Or KeyDown(KEY_LEFT)
				velocity.x = -speed
			End
			
			If KeyDown(rightKey) Or KeyDown(KEY_RIGHT)
				velocity.x = speed
			End
			
			If KeyDown(upKey) Or KeyDown(KEY_UP)
				velocity.y = -speed
			End
			
			If KeyDown(downKey) Or KeyDown(KEY_DOWN)
				velocity.y = speed
			End
		Else If controls = USE_TOUCH
			If TouchDown(0) And Not TouchDown(1)
				If TouchX(0) > position.x
					velocity.x = speed
				Else If TouchX(0) < position.x
					velocity.x = -speed
				End
			End
		End
				
		SetPosition(position.x + velocity.x, position.y + velocity.y)
	End
	
	Method UpdateCornerPoints()
		topLeft.Set(position.x, position.y)
		topRight.Set(position.x + PLAYER_WIDTH, position.y)
		botLeft.Set(position.x, position.y + PLAYER_HEIGHT)
		botRight.Set(position.x + PLAYER_WIDTH, position.y + PLAYER_HEIGHT)
	End
	
	Method SetPosition(x:Float, y:Float)
		position.Set(x, y)
		UpdateCornerPoints()
		box.position.Set(x, y)
	End
	
	Method Fire:Bullet(bulletImages:Image[])
		Return New Bullet(bullet_speed, position.x + PLAYER_WIDTH/2, position.y, colors, bulletImages[currentImg])
	End
	
	Method ChangeColor()
		' Rotate through the colors
		If colors[0] = 255
			colors = [0, 255, 0]
			currentImg = 1
		Else If colors[1] = 255
			colors = [0, 0, 255]
			currentImg = 2
		Else
			colors = [255, 0, 0]
			currentImg = 0
		End
	End
	
	Method SetSpeed(newSpeed:Float)
		speed = newSpeed
	End
End

Class Bullet
	Field position:Vec2D
	Field velocity:Vec2D
	Field speed:Float
	Field colors:Int[] = [0, 0, 0]
	Field bulletImg:Image
	
	Field box:CollisionRect
	
	Method New(speed:Float, x:Float, y:Float, parent_colors:Int[], img:Image)
		position = New Vec2D(x, y)
		velocity = New Vec2D(0, speed)

		colors = parent_colors
		bulletImg = img
		
		box = New CollisionRect(x, y, BULLET_WIDTH, BULLET_HEIGHT)
	End
		
	Method Draw()
		SetColor(colors[0], colors[1], colors[2])
		DrawImage(bulletImg, position.x, position.y)
	End
	
	Method Update()
		SetPosition(position.x + velocity.x, position.y + velocity.y)
	End

	Method SetPosition(x:Float, y:Float)
		position.Set(x, y)
		box.position.Set(x, y)
	End
End

Class Enemy
	Field position:Vec2D
	Field velocity:Vec2D
	Field colors:Int[] = [0, 0, 0]
	Field box:CollisionRect
	Field colorImages:Image[]
	Field currentImg:Int
	
	Method New(yspeed:Float, color:Float, x:Float, y:Float, enemyImages:Image[])
		position = New Vec2D(x, y)
		velocity = New Vec2D(0, yspeed)
		colorImages = enemyImages
		box = New CollisionRect(x, y, ENEMY_WIDTH, ENEMY_HEIGHT)
		If color < 1.0
			colors[0] = 255
			currentImg = 0
		Else If color < 2.0 And color >= 1.0
			colors[1] = 255
			currentImg = 1
		Else If color < 3.0 And color >= 2.0
			colors[2] = 255
			currentImg = 2
		Else
			colors = [128, 128, 128]
		End
		
	End
	
	Method Draw()
		SetColor(colors[0], colors[1], colors[2])
		DrawImage(colorImages[currentImg], position.x, position.y)
	End
	
	Method Update()
		SetPosition(position.x + velocity.x, position.y + velocity.y)
	End
	
	Method SetPosition(x:Float, y:Float)
		position.Set(x, y)
		box.position.Set(x, y)
	End
	
End

Class CollisionRect
	Field position:Vec2D
	Field width:Int
	Field height:Int
	
	Method New(x:Float, y:Float, wide:Int, high:Int)
		position = New Vec2D(x, y)
		width = wide
		height = high
	End
End
Import Mojo

Const TILE_WIDTH:Int = 48
Const TILE_HEIGHT:Int = 48

Const SCREEN_WIDTH:Int = 640
Const SCREEN_HEIGHT:Int = 480

Const PLAYER_WIDTH:Int = 32
Const PLAYER_HEIGHT:Int = 48

Const BULLET_WIDTH:Int = 4
Const BULLET_HEIGHT:Int = 22

Const ENEMY_WIDTH:Int = 32
Const ENEMY_HEIGHT:Int = 32

' class to handle in game variables
Class GameVars
	
End

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
	
	Field speed:Float = 3.0
	Field friction:Float = 0.2
	
	Field leftKey:Int = KEY_A
	Field rightKey:Int = KEY_D
	Field upKey:Int = KEY_W
	Field downKey:Int = KEY_S
	Field fireKey:Int = KEY_SPACE
	Field rotateKey:Int = KEY_R
	
	Field topLeft:Vec2D = New Vec2D()
	Field topRight:Vec2D = New Vec2D()
	Field botLeft:Vec2D = New Vec2D()
	Field botRight:Vec2D = New Vec2D()
	Field box:CollisionRect
	
	Field color:String = "BLUE"
	
	Method New(x:Float, y:Float)
		position = New Vec2D(x, y)
		velocity = New Vec2D()
		box = New CollisionRect(x, y, PLAYER_WIDTH, PLAYER_HEIGHT)
	End
	
	Method Draw()
		SetColor(0, 0, 255)
		DrawRect(position.x - PLAYER_WIDTH/2, position.y - PLAYER_HEIGHT/2, PLAYER_WIDTH, PLAYER_HEIGHT)
	End
	
	Method Update()
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
		
		If KeyDown(leftKey)
			velocity.x = -speed
		End
		
		If KeyDown(rightKey)
			velocity.x = speed
		End
		
		If KeyDown(upKey)
			velocity.y = -speed
		End
		
		If KeyDown(downKey)
			velocity.y = speed
		End
		
		SetPosition(position.x + velocity.x, position.y + velocity.y)
	End
	
	Method UpdateCornerPoints()
		topLeft.Set(position.x - PLAYER_WIDTH/2, position.y - PLAYER_HEIGHT/2)
		topRight.Set(position.x + PLAYER_WIDTH/2, position.y - PLAYER_HEIGHT/2)
		botLeft.Set(position.x - PLAYER_WIDTH/2, position.y + PLAYER_HEIGHT/2)
		botRight.Set(position.x + PLAYER_WIDTH/2, position.y + PLAYER_HEIGHT/2)
	End
	
	Method SetPosition(x:Float, y:Float)
		position.Set(x, y)
		UpdateCornerPoints()
		box.position.Set(x, y)
	End
	
	Method Fire:Bullet()
		Return New Bullet(-4.0, color, position.x, position.y)
	End
End

Class Bullet
	Field position:Vec2D
	Field velocity:Vec2D
	Field speed:Float
	Field type:String
	Field box:CollisionRect
	
	Method New(speed:Float, color:String, x:Float, y:Float)
		position = New Vec2D(x, y)
		velocity = New Vec2D(0, speed)
		type = color
		box = New CollisionRect(x, y, BULLET_WIDTH, BULLET_HEIGHT)
	End
		
	Method Draw()
		SetColor(0, 0, 255)
		DrawRect(position.x, position.y, BULLET_WIDTH, BULLET_HEIGHT)
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
	Field speed:Float
	Field RED:Int = 0
	Field BLUE:Int = 0
	Field GREEN:Int = 0
	Field box:CollisionRect
	
	Method New(speed:Float, color:Float, x:Float, y:Float)
		position = New Vec2D(x, y)
		velocity = New Vec2D(0, speed)
		box = New CollisionRect(x, y, ENEMY_WIDTH, ENEMY_HEIGHT)
		If color < 1.0
			RED = 255
		Else If color < 2.0 And color >= 1.0
			GREEN = 255
		Else If color < 3.0 And color >= 2.0
			BLUE = 255
		Else
			RED = 128
			GREEN = 128
			BLUE = 128
		End
		
	End
	
	Method Draw()
		SetColor(RED, GREEN, BLUE)
		DrawRect(position.x, position.y, ENEMY_WIDTH, ENEMY_HEIGHT)
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
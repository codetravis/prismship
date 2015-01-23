Import Mojo

Const TILE_WIDTH:Int = 48
Const TILE_HEIGHT:Int = 48

Const SCREEN_WIDTH:Int = 640
Const SCREEN_HEIGHT:Int = 480

Const PLAYER_WIDTH:Int = 32
Const PLAYER_HEIGHT:Int = 48

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
	
	Field color:String = "blue"
	
	Method New(x:Float, y:Float)
		position = New Vec2D(x, y)
		velocity = New Vec2D()
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
	
	Method SetPosition(x:Float, y:Float)
		position.Set(x, y)
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
	
	Method New(speed:Float, color:String, x:Float, y:Float)
		position = New Vec2D(x, y)
		velocity = New Vec2D(0, speed)
		type = color
	End
		
	Method Draw()
		SetColor(0, 0, 255)
		DrawRect(position.x, position.y, 4, 22)
	End
	
	Method Update()
		SetPosition(position.x + velocity.x, position.y + velocity.y)
	End

	Method SetPosition(x:Float, y:Float)
		position.Set(x, y)
	End
End
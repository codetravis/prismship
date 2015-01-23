Import prismship

Const STATE_MENU:Int = 0
Const STATE_GAME:Int = 1
Const STATE_DEATH:Int = 2

Class PrismShipGame Extends App
	Field gameState:Int = STATE_MENU
	Field player:Player = New Player(120, 300)
	
	Field bullets:List<Bullet> = New List<Bullet>()
	
	Method OnCreate()
		SetUpdateRate(60)
		
	End
	
	Method OnUpdate()
		Select gameState
			Case STATE_MENU
				If KeyHit(KEY_ENTER)
					gameState = STATE_GAME
				End
			Case STATE_GAME
				UpdatePlayer(player)
				UpdateBullets(bullets)
				If KeyHit(KEY_SPACE)
					bullets.AddLast(player.Fire())
				End
				
			Case STATE_DEATH
		End
	End
	
	Method OnRender()
		Cls(255, 255, 255)
		
		Select gameState
			Case STATE_MENU
				DrawText("PrismShip -- Press ENTER to play!", 320, 200, 0.5)
			Case STATE_GAME
				PushMatrix()
				
				For Local bullet:Bullet = Eachin bullets
					If bullet.position.y < 0
						bullets.Remove(bullet)
					Else
						bullet.Draw()
					End
				End

				player.Draw()				
				PopMatrix()
			Case STATE_DEATH
		End	
	End
	
	Method UpdatePlayer(player:Player)
		player.Update()
		
		If player.position.x < PLAYER_WIDTH / 2
			player.SetPosition(PLAYER_WIDTH / 2, player.position.y)
		Else If player.position.x > SCREEN_WIDTH - PLAYER_WIDTH
			player.SetPosition(SCREEN_WIDTH - PLAYER_WIDTH, player.position.y)
		End
		
		If player.position.y > SCREEN_HEIGHT - PLAYER_HEIGHT
			player.SetPosition(player.position.x, SCREEN_HEIGHT - PLAYER_HEIGHT)
		Else If player.position.y < PLAYER_HEIGHT / 2
			player.SetPosition(player.position.x, PLAYER_HEIGHT / 2)
		End
		
	End
	
	Method UpdateBullets(bullets:List<Bullet>)
		For Local bullet:Bullet = Eachin bullets
			bullet.Update()
		End
	End

End

Function Main()
	New PrismShipGame()
End
Import prismship

Const STATE_MENU:Int = 0
Const STATE_GAME:Int = 1
Const STATE_DEATH:Int = 2

Class PrismShipGame Extends App
	Field gameState:Int = STATE_MENU
	Field player:Player = New Player(120, 300)
	
	Field bullets:List<Bullet> = New List<Bullet>()
	Field enemies:List<Enemy>  = New List<Enemy>()
	
	Field lastEnemyTime:Float = 0.0
	Field nextEnemyDiff:Float = 1000
	
	Method OnCreate()
		SetUpdateRate(60)
		lastEnemyTime = Millisecs()
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
				GenerateEnemy()
				UpdateEnemies(enemies)
				DestroyThings(bullets, enemies)
				
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
				
				For Local enemy:Enemy = Eachin enemies
					If enemy.position.y > SCREEN_HEIGHT
						enemies.Remove(enemy)
					Else
						enemy.Draw()
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
	
	Method UpdateEnemies(enemies:List<Enemy>)
		For Local enemy:Enemy = Eachin enemies
			enemy.Update()
		End
	End
	
	Method DestroyThings(bullets:List<Bullet>, enemies:List<Enemy>)
		For Local bullet:Bullet = Eachin bullets
			For Local enemy:Enemy = Eachin enemies
				If Collided(bullet.box, enemy.box)
					bullets.Remove(bullet)
					enemies.Remove(enemy)
				End
			End
		End
	End
	
	Method GenerateEnemy()
		If Millisecs() - lastEnemyTime >= nextEnemyDiff
			enemies.AddLast(New Enemy(Rnd(2.0, 5.0), Rnd(3.0), Rnd() * SCREEN_WIDTH, -32.0))
			lastEnemyTime = Millisecs()
			nextEnemyDiff = 1000 * Rnd(0.5, 2.0)
		End
	End
	
	Method Collided(box1:CollisionRect, box2:CollisionRect)
		If (box1.position.x < box2.position.x + box2.width And
		   box1.position.x + box1.width > box2.position.x And
		   box1.position.y < box2.position.y + box2.height And
		   box1.height + box1.position.y > box2.position.y)
			Return True
		Else
			Return False
		End
	End

End

Function Main()
	New PrismShipGame()
End
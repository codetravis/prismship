Import prismship
Import brl.httprequest

Const STATE_MENU:Int = 0
Const STATE_GAME:Int = 1
Const STATE_DEATH:Int = 2
Const STATE_HELP:Int = 3

Class PrismShipGame Extends App Implements IOnHttpRequestComplete
	Field gameState:Int = STATE_MENU
	Field score:Int = 0
	Field initials:String = ""
	
	Field bullets:List<Bullet> = New List<Bullet>()
	Field enemies:List<Enemy>  = New List<Enemy>()
	
	Field lastEnemyTime:Float = 0.0
	Field nextEnemyDiff:Float = 1000
	Field lastColorChange:Float = 0.0
	Field colorChangeTime:Float = 7000
	Field controls:String = ""
	
	Field redPlayerImg:Image
	Field bluePlayerImg:Image
	Field greenPlayerImg:Image
	Field playerImages:Image[]
	
	Field redEnemyImg:Image
	Field blueEnemyImg:Image
	Field greenEnemyImg:Image
	Field enemyImages:Image[]
	
	Field redBulletImg:Image
	Field greenBulletImg:Image
	Field blueBulletImg:Image
	Field bulletImages:Image[]
	
	Field getReq:HttpRequest
	Field postReq:HttpRequest
	Field highScoreServer:String = "https://prismship.herokuapp.com/"
	Field highScores:String
	
	Field keyboard_enabled:Bool
	
	Field player:Player
	
	Method OnCreate()
		Print "Creating Game"
		SetUpdateRate(60)
		lastEnemyTime = Millisecs()
		Seed = Millisecs()
		
		' Load Enemy Images
		redEnemyImg = LoadImage("ENEMY_RED.png")
		blueEnemyImg = LoadImage("ENEMY_BLUE.png")
		greenEnemyImg = LoadImage("ENEMY_GREEN.png")
		enemyImages = [redEnemyImg, greenEnemyImg, blueEnemyImg]
		
		' Load Player Images
		redPlayerImg = LoadImage("PLAYER_RED.png")
		bluePlayerImg = LoadImage("PLAYER_BLUE.png")
		greenPlayerImg = LoadImage("PLAYER_GREEN.png")
		playerImages = [redPlayerImg, greenPlayerImg, bluePlayerImg]
		
		' Load Bullet Images
		redBulletImg = LoadImage("BULLET_RED.png")
		blueBulletImg = LoadImage("BULLET_BLUE.png")
		greenBulletImg = LoadImage("BULLET_GREEN.png")
		bulletImages = [redBulletImg, greenBulletImg, blueBulletImg]
		
		' Create Player
		player = New Player(220, 480 - PLAYER_HEIGHT * 2, 0, USE_KEYBOARD, 400, playerImages)
		keyboard_enabled = False
		
	End
	
	Method OnUpdate()
		Select gameState
			Case STATE_MENU
				If KeyHit(KEY_ENTER)
					player.SetSpeed(3.0)
					controls = "KEYBOARD"
					gameState = STATE_HELP
				Else If (TouchDown(0) And 
					TouchX(0) > 200 And
					TouchX(0) < 400 And
					TouchY(0) > 300 And
					TouchY(0) < 320)
					player.SetSpeed(4.0)
					controls = "TOUCH"
					gameState = STATE_HELP
				End
			Case STATE_HELP
				If (Not keyboard_enabled)
					EnableKeyboard()
					keyboard_enabled = True
				End
				Local char = GetChar()
				If (char = CHAR_ENTER)
					DisableKeyboard()
					keyboard_enabled = False
					gameState = STATE_GAME
				Else If (char = CHAR_BACKSPACE Or char = CHAR_DELETE)
					If (initials.Length() <= 1)
						initials = ""
					Else
						initials = initials[..-1]
					End
				Else If (char > 0 And initials.Length() < 3)
					initials += String.FromChar(char)
				End
			Case STATE_GAME
				UpdatePlayer(player)
				UpdateBullets(bullets)
				If KeyHit(KEY_SPACE) Or (TouchDown(0) And TouchDown(1))
					' Only create a bullet if the player has not fired recently
					If Millisecs() - player.lastBullet >= player.fireRate
						bullets.AddLast(player.Fire(bulletImages))
						player.lastBullet = Millisecs
					End
				End
				GenerateEnemy()
				UpdateEnemies(enemies)
				DestroyThings(bullets, enemies)
				CheckDeath(player, enemies)
				ChangeColor(player)
				
			Case STATE_DEATH
				If KeyHit(KEY_ENTER) Or TouchDown(0)
					Reset()
				End
		End
		UpdateAsyncEvents
	End
	
	Method OnRender()
		Cls(100, 100, 100)
		
		Select gameState
			Case STATE_MENU
				DrawText("PrismShip", 320, 200, 0.5)
				DrawText("Hit ENTER to use keyboard controls", 320, 250, 0.5)
				DrawText("Or Touch Screen HERE To use touch controls", 320, 300, 0.5)
			Case STATE_HELP
				If controls = "KEYBOARD"
					DrawText("Keyboard Controls", 320, 100, 0.5)
					DrawText("-- Use W, A, S, D to Move", 320, 130, 0.5)
					DrawText("-- Use SPACE key to Fire", 320, 160, 0.5)
					DrawText("Enter your initials: " + initials, 320, 190, 0.5)
					player.controls = USE_KEYBOARD
				Else If controls = "TOUCH"
					DrawText("Touch Controls", 320, 100, 0.5)
					DrawText("-- Touch to one side of the ship to Move in that direction", 320, 130, 0.5)
					DrawText("-- Touch with 2 fingers to Fire", 320, 160, 0.5)
					DrawText("Enter your initials to play: " + initials, 320, 190, 0.5)
					player.controls = USE_TOUCH
				End
				DrawText("Shoot Blocks the same color as you to score", 320, 250, 0.5)
				DrawText("Get hit by a block and you lose", 320, 280, 0.5)
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
				SetColor(255, 255, 255)
				DrawText("Score: " + score, 20, 20, 0, 0)				
				PopMatrix()
			Case STATE_DEATH
				DrawText("GAME OVER", 320, 115, 0.5)
				DrawText("INITIALS: " + initials, 320, 130, 0.5)
				DrawText("SCORE: " + score, 320, 160, 0.5)
				DrawText("Hit ENTER or Touch the screen to try again!", 320, 190, 0.5)
				DrawScores(220)
		End	
	End
	
	Method DrawScores(beginHeight:Float)
		Local scoreList:String[] = highScores.Split("~n")
		Local ypos:Float = beginHeight
		For Local i:Int = 0 Until scoreList.Length
			DrawText(scoreList[i], 320, ypos)
			ypos += 10
		Next
	End
	
	Method UpdatePlayer(player:Player)
		player.Update()
		
		' update the player position
		If player.position.x < 0
			player.SetPosition(0, player.position.y)
		End
		If player.position.x > SCREEN_WIDTH - PLAYER_WIDTH
			player.SetPosition(SCREEN_WIDTH - PLAYER_WIDTH, player.position.y)
		End
		
		If player.position.y > SCREEN_HEIGHT - PLAYER_HEIGHT
			player.SetPosition(player.position.x, SCREEN_HEIGHT - PLAYER_HEIGHT)
		End
		If player.position.y < 0
			player.SetPosition(player.position.x, 0)
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
		' Destroy Bullets that hit enemies
		' Destroy Enemies that are hit by same colored bullets
		For Local bullet:Bullet = Eachin bullets
			For Local enemy:Enemy = Eachin enemies
				If Collided(bullet.box, enemy.box) And SameColor(bullet, enemy)
					bullets.Remove(bullet)
					enemies.Remove(enemy)
					score += 10
				Else If Collided(bullet.box, enemy.box) And Not SameColor(bullet, enemy)
					bullets.Remove(bullet)
				End
			End
		End
	End
	
	Method CheckDeath(player:Player, enemies:List<Enemy>)
		' If player is hit by any Enemy, DIE
		For Local enemy:Enemy = Eachin enemies
			If Collided(player.box, enemy.box)
				gameState = STATE_DEATH
				PostScore()
			End
		End
	End
	
	Method GenerateEnemy()
		If Millisecs() - lastEnemyTime >= Max(nextEnemyDiff - score, 200.0)
			enemies.AddLast(New Enemy(Rnd(2.0, 5.0), Rnd(3.0), 20.0 + Rnd() * (SCREEN_WIDTH - 40), -32.0, enemyImages))
			lastEnemyTime = Millisecs()
			nextEnemyDiff = 1000 * Rnd(0.5, 1.0)
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
	
	Method SameColor(bullet:Bullet, enemy:Enemy)
		If (bullet.colors[0] = enemy.colors[0] And
			bullet.colors[1] = enemy.colors[1] And
			bullet.colors[2] = enemy.colors[2])
			Return True
		Else
			Return False
		End
	End
	
	Method ChangeColor(player:Player)
		If Millisecs() - lastColorChange >= colorChangeTime
			player.ChangeColor()
			lastColorChange = Millisecs()
		End 
	End
	
	Method Reset()
		player.Reset()
		enemies.Clear()
		bullets.Clear()
		score = 0
		gameState = STATE_MENU
	End
	
	Method GetScores()
		getReq = New HttpRequest("GET", highScoreServer, Self)
		Print "Sending Get Request"
		getReq.Send
		Print "Get Request Sent"
	End
	
	Method PostScore()
		postReq = New HttpRequest("POST", highScoreServer + initials + "/" + score, Self)
		Print highScoreServer + initials + "/" + score
		postReq.Send()
	End
	
	Method OnHttpRequestComplete:Void(req:HttpRequest)
		If req = getReq
			Print "Get Complete"
		Else
			Print "Post Complete"
			GetScores()
		End
		
		Print req.Status()
		'Print req.ResponseText()
		highScores = req.ResponseText()
	End

End

Function Main()
	New PrismShipGame()
End
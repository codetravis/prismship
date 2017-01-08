
//${PACKAGE_BEGIN}
package com.monkeycoder.monkeygame;
//${PACKAGE_END}

//${IMPORTS_BEGIN}
import java.lang.Math;
import java.lang.reflect.Array;
import java.util.Vector;
import java.text.NumberFormat;
import java.text.ParseException;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.lang.reflect.*;
import android.os.*;
import android.app.*;
import android.media.*;
import android.view.*;
import android.graphics.*;
import android.content.*;
import android.util.*;
import android.hardware.*;
import android.widget.*;
import android.view.inputmethod.*;
import android.content.res.*;
import android.opengl.*;
import android.text.*;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import com.monkey.LangUtil;
import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;
//${IMPORTS_END}

class MonkeyConfig{
//${CONFIG_BEGIN}
static final String ADMOB_ANDROID_TEST_DEVICE1="TEST_EMULATOR";
static final String ADMOB_ANDROID_TEST_DEVICE2="ABCDABCDABCDABCDABCDABCDABCDABCD";
static final String ADMOB_ANDROID_TEST_DEVICE3="";
static final String ADMOB_ANDROID_TEST_DEVICE4="";
static final String ADMOB_PUBLISHER_ID="abcdabcdabcdabc";
static final String ANDROID_APP_LABEL="Monkey Game";
static final String ANDROID_APP_PACKAGE="com.monkeycoder.monkeygame";
static final String ANDROID_GAMEPAD_ENABLED="0";
static final String ANDROID_KEY_ALIAS="release-key-alias";
static final String ANDROID_KEY_ALIAS_PASSWORD="password";
static final String ANDROID_KEY_STORE="../../release-key.keystore";
static final String ANDROID_KEY_STORE_PASSWORD="password";
static final String ANDROID_LANGUTIL_ENABLED="1";
static final String ANDROID_LIBGOOGLEPLAY_AVAILABLE="1";
static final String ANDROID_MANIFEST_ACTIVITY="\n";
static final String ANDROID_MANIFEST_APPLICATION="\n";
static final String ANDROID_MANIFEST_MAIN="\n";
static final String ANDROID_NATIVE_GL_ENABLED="0";
static final String ANDROID_SCREEN_ORIENTATION="user";
static final String ANDROID_SIGN_APP="0";
static final String ANDROID_VERSION_CODE="1";
static final String ANDROID_VERSION_NAME="1.0";
static final String BINARY_FILES="*.bin|*.dat";
static final String BRL_GAMETARGET_IMPLEMENTED="1";
static final String BRL_HTTPREQUEST_IMPLEMENTED="1";
static final String BRL_THREAD_IMPLEMENTED="1";
static final String CD="";
static final String CONFIG="release";
static final String HOST="macos";
static final String IMAGE_FILES="*.png|*.jpg|*.gif|*.bmp";
static final String LANG="java";
static final String MODPATH="";
static final String MOJO_DRIVER_IMPLEMENTED="1";
static final String MOJO_HICOLOR_TEXTURES="1";
static final String MOJO_IMAGE_FILTERING_ENABLED="1";
static final String MUSIC_FILES="*.wav|*.ogg|*.mp3|*.m4a";
static final String OPENGL_GLES20_ENABLED="0";
static final String SAFEMODE="0";
static final String SOUND_FILES="*.wav|*.ogg|*.mp3|*.m4a";
static final String TARGET="android";
static final String TEXT_FILES="*.txt|*.xml|*.json";
//${CONFIG_END}
}

//${TRANSCODE_BEGIN}

// Java Monkey runtime.
//
// Placed into the public domain 24/02/2011.
// No warranty implied; use at your own risk.



class bb_std_lang{

	//***** Error handling *****

	static String errInfo="";
	static Vector errStack=new Vector();
	
	static float D2R=0.017453292519943295f;
	static float R2D=57.29577951308232f;
	
	static NumberFormat numberFormat=NumberFormat.getInstance();
	
	static boolean[] emptyBoolArray=new boolean[0];
	static int[] emptyIntArray=new int[0];
	static float[] emptyFloatArray=new float[0];
	static String[] emptyStringArray=new String[0];
	
	static void pushErr(){
		errStack.addElement( errInfo );
	}
	
	static void popErr(){
		if( errStack.size()==0 ) throw new Error( "STACK ERROR!" );
		errInfo=(String)errStack.remove( errStack.size()-1 );
	}
	
	static String stackTrace(){
		if( errInfo.length()==0 ) return "";
		String str=errInfo+"\n";
		for( int i=errStack.size()-1;i>0;--i ){
			str+=(String)errStack.elementAt(i)+"\n";
		}
		return str;
	}
	
	static int print( String str ){
		System.out.println( str );
		return 0;
	}
	
	static int error( String str ){
		throw new RuntimeException( str );
	}
	
	static String makeError( String err ){
		if( err.length()==0 ) return "";
		return "Monkey Runtime Error : "+err+"\n\n"+stackTrace();
	}
	
	static int debugLog( String str ){
		print( str );
		return 0;
	}
	
	static int debugStop(){
		error( "STOP" );
		return 0;
	}
	
	//***** String stuff *****

	static public String[] stringArray( int n ){
		String[] t=new String[n];
		for( int i=0;i<n;++i ) t[i]="";
		return t;
	}
	
	static String slice( String str,int from ){
		return slice( str,from,str.length() );
	}
	
	static String slice( String str,int from,int term ){
		int len=str.length();
		if( from<0 ){
			from+=len;
			if( from<0 ) from=0;
		}else if( from>len ){
			from=len;
		}
		if( term<0 ){
			term+=len;
		}else if( term>len ){
			term=len;
		}
		if( term>from ) return str.substring( from,term );
		return "";
	}
	
	static public String[] split( String str,String sep ){
		if( sep.length()==0 ){
			String[] bits=new String[str.length()];
			for( int i=0;i<str.length();++i){
				bits[i]=String.valueOf( str.charAt(i) );
			}
			return bits;
		}else{
			int i=0,i2,n=1;
			while( (i2=str.indexOf( sep,i ))!=-1 ){
				++n;
				i=i2+sep.length();
			}
			String[] bits=new String[n];
			i=0;
			for( int j=0;j<n;++j ){
				i2=str.indexOf( sep,i );
				if( i2==-1 ) i2=str.length();
				bits[j]=slice( str,i,i2 );
				i=i2+sep.length();
			}
			return bits;
		}
	}
	
	static public String join( String sep,String[] bits ){
		if( bits.length<2 ) return bits.length==1 ? bits[0] : "";
		StringBuilder buf=new StringBuilder( bits[0] );
		boolean hasSep=sep.length()>0;
		for( int i=1;i<bits.length;++i ){
			if( hasSep ) buf.append( sep );
			buf.append( bits[i] );
		}
		return buf.toString();
	}
	
	static public String replace( String str,String find,String rep ){
		int i=0;
		for(;;){
			i=str.indexOf( find,i );
			if( i==-1 ) return str;
			str=str.substring( 0,i )+rep+str.substring( i+find.length() );
			i+=rep.length();
		}
	}
	
	static public String fromChars( int[] chars ){
		int n=chars.length;
		char[] chrs=new char[n];
		for( int i=0;i<n;++i ){
			chrs[i]=(char)chars[i];
		}
		return new String( chrs,0,n );
	}
	
	static int[] toChars( String str ){
		int[] arr=new int[str.length()];
		for( int i=0;i<str.length();++i ) arr[i]=(int)str.charAt( i );
		return arr;
	}
	
	//***** Array Stuff *****
	
	static int length( Object arr ){
		return arr!=null ? Array.getLength( arr ) : 0;
	}
	
	static Object sliceArray( Object arr,int from ){
		if( arr==null ) return null;
		return sliceArray( arr,from,Array.getLength( arr ) );
	}
	
	static Object sliceArray( Object arr,int from,int term ){
		if( arr==null ) return null;
		int len=Array.getLength( arr );
		if( from<0 ){
			from+=len;
			if( from<0 ) from=0;
		}else if( from>len ){
			from=len;
		}
		if( term<0 ){
			term+=len;
		}else if( term>len ){
			term=len;
		}
		if( term<from ) term=from;
		int newlen=term-from;
		Object res=Array.newInstance( arr.getClass().getComponentType(),newlen );
		if( newlen>0 ) System.arraycopy( arr,from,res,0,newlen );
		return res;
	}
	
	static String[] resize( String[] arr,int newlen ){
		if( arr==null ) return stringArray( newlen );
		int len=arr.length;
		String[] res=new String[newlen];
		int n=Math.min( len,newlen );
		if( n>0 ) System.arraycopy( arr,0,res,0,n );
		while( len<newlen ) res[len++]="";
		return res;		
	}
	
	static Object resize( Object arr,int newlen,Class elemTy ){
		if( arr==null ) return Array.newInstance( elemTy,newlen );
		int len=Array.getLength( arr );
		Object res=Array.newInstance( elemTy,newlen );
		int n=Math.min( len,newlen );
		if( n>0 ) System.arraycopy( arr,0,res,0,n );
		return res;
	}
	
	public static <T> T as( Class<T> t,Object o ){
		return t.isInstance( o ) ? t.cast( o ) : null;
	}
}

class ThrowableObject extends RuntimeException{
	ThrowableObject(){
		super( "Uncaught Monkey Exception" );
	}
}



class BBGameEvent{
	static final int KeyDown=1;
	static final int KeyUp=2;
	static final int KeyChar=3;
	static final int MouseDown=4;
	static final int MouseUp=5;
	static final int MouseMove=6;
	static final int TouchDown=7;
	static final int TouchUp=8;
	static final int TouchMove=9;
	static final int MotionAccel=10;
}

class BBGameDelegate{
	void StartGame(){}
	void SuspendGame(){}
	void ResumeGame(){}
	void UpdateGame(){}
	void RenderGame(){}
	void KeyEvent( int event,int data ){}
	void MouseEvent( int event,int data,float x,float y ){}
	void TouchEvent( int event,int data,float x,float y ){}
	void MotionEvent( int event,int data,float x,float y,float z ){}
	void DiscardGraphics(){}
}

class BBDisplayMode{
	public int width;
	public int height;
	public int format;
	public int hertz;
	public int flags;
	public BBDisplayMode(){}
	public BBDisplayMode( int width,int height ){ this.width=width;this.height=height; }
}

abstract class BBGame{

	protected static BBGame _game;

	protected BBGameDelegate _delegate;
	protected boolean _keyboardEnabled;
	protected int _updateRate;
	protected boolean _debugExs;
	protected boolean _started;
	protected boolean _suspended;
	protected long _startms;
	
	public BBGame(){
		_game=this;
		_debugExs=MonkeyConfig.CONFIG.equals( "debug" );
		_startms=System.currentTimeMillis();
	}
	
	public static BBGame Game(){
		return _game;
	}
	
	public boolean Started(){
		return _started;
	}
	
	public boolean Suspended(){
		return _suspended;
	}

	public void SetDelegate( BBGameDelegate delegate ){
		_delegate=delegate;
	}
	
	public BBGameDelegate Delegate(){
		return _delegate;
	}
	
	public void SetKeyboardEnabled( boolean enabled){
		_keyboardEnabled=enabled;
	}
	
	public boolean KeyboardEnabled(){
		return _keyboardEnabled;
	}

	public void SetUpdateRate( int hertz ){
		_updateRate=hertz;
	}
	
	public int UpdateRate(){
		return _updateRate;
	}

	public int Millisecs(){
		return (int)(System.currentTimeMillis()-_startms);
	}
	
	public void GetDate( int[] date ){
		int n=date.length;
		if( n>0 ){
			Calendar c=Calendar.getInstance();
			date[0]=c.get( Calendar.YEAR );
			if( n>1 ){
				date[1]=c.get( Calendar.MONTH )+1;
				if( n>2 ){
					date[2]=c.get( Calendar.DATE );
					if( n>3 ){
						date[3]=c.get( Calendar.HOUR_OF_DAY );
						if( n>4 ){
							date[4]=c.get( Calendar.MINUTE );
							if( n>5 ){
								date[5]=c.get( Calendar.SECOND );
								if( n>6 ){
									date[6]=c.get( Calendar.MILLISECOND );
								}
							}
						}
					}
				}
			}
		}
	}
	
	public int SaveState( String state ){
		return -1;
	}

	public String LoadState(){
		return "";
	}

	public String LoadString( String path ){
		byte[] bytes=LoadData( path );
		if( bytes!=null ) return loadString( bytes );
		return "";
	}
	
	public int CountJoysticks( boolean update ){
		return 0;
	}
	
	public boolean PollJoystick( int port,float[] joyx,float[] joyy,float[] joyz,boolean[] buttons ){
		return false;
	}
	
	public void SetMouseVisible( boolean visible ){
	}

	int GetDeviceWidth(){
		return 0;
	}
	
	int GetDeviceHeight(){
		return 0;
	}
	
	void SetDeviceWindow( int width,int height,int flags ){
	}
	
	BBDisplayMode[] GetDisplayModes(){
		return new BBDisplayMode[0];
	}
	
	BBDisplayMode GetDesktopMode(){
		return null;
	}
	
	void SetSwapInterval( int interval ){
	}
	
	public void OpenUrl( String url ){	
	}
	
	String PathToFilePath( String path ){
		return "";
	}
	
	//***** Java Game *****
	
	public RandomAccessFile OpenFile( String path,String mode ){
		try{
			return new RandomAccessFile( PathToFilePath( path ),mode );
		}catch( IOException ex ){
		}
		return null;
	}
	
	public InputStream OpenInputStream( String path ){
		try{
			if( path.startsWith( "http:" ) || path.startsWith( "https:" ) ){
				URL url=new URL( path );
				URLConnection con=url.openConnection();
				return new BufferedInputStream( con.getInputStream() );
			}
			return new FileInputStream( PathToFilePath( path ) );
		}catch( IOException ex ){
		}
		return null;
	}
	
	byte[] LoadData( String path ){
		try{
			InputStream in=OpenInputStream( path );
			if( in==null ) return null;
			
			ByteArrayOutputStream out=new ByteArrayOutputStream(1024);
			byte[] buf=new byte[4096];
			
			for(;;){
				int n=in.read( buf );
				if( n<0 ) break;
				out.write( buf,0,n );
			}
			in.close();
			return out.toByteArray();
			
		}catch( IOException e ){
		}
		return null;
	}
	
	//***** INTERNAL *****
	
	public void Quit(){
		System.exit( 0 );
	}
	
	public void Die( RuntimeException ex ){
	
		String msg=ex.getMessage();
		if( msg!=null && msg.equals("") ){
			Quit();
			return;
		}
		if( _debugExs ){
			if( msg==null ) msg=ex.toString();
			bb_std_lang.print( "Monkey Runtime Error : "+msg );
			bb_std_lang.print( bb_std_lang.stackTrace() );
		}
		throw ex;
	}

	void StartGame(){
	
		if( _started ) return;
		_started=true;
		
		try{
			synchronized( _delegate ){
				_delegate.StartGame();
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void SuspendGame(){
	
		if( !_started || _suspended ) return;
		_suspended=true;
		
		try{
			synchronized( _delegate ){
				_delegate.SuspendGame();
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void ResumeGame(){

		if( !_started || !_suspended ) return;
		_suspended=false;
		
		try{
			synchronized( _delegate ){
				_delegate.ResumeGame();
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void UpdateGame(){

		if( !_started || _suspended ) return;

		try{
			synchronized( _delegate ){
				_delegate.UpdateGame();
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void RenderGame(){
	
		if( !_started ) return;
		
		try{
			synchronized( _delegate ){
				_delegate.RenderGame();
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void KeyEvent( int event,int data ){

		if( !_started ) return;
	
		try{
			synchronized( _delegate ){
				_delegate.KeyEvent( event,data );
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void MouseEvent( int event,int data,float x,float y ){

		if( !_started ) return;
		
		try{
			synchronized( _delegate ){
				_delegate.MouseEvent( event,data,x,y );
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void TouchEvent( int event,int data,float x,float y ){

		if( !_started ) return;
		
		try{
			synchronized( _delegate ){
				_delegate.TouchEvent( event,data,x,y );
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void MotionEvent( int event,int data,float x,float y,float z ){

		if( !_started ) return;
		
		try{
			synchronized( _delegate ){
				_delegate.MotionEvent( event,data,x,y,z );
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	void DiscardGraphics(){

		if( !_started ) return;
		
		try{
			synchronized( _delegate ){
				_delegate.DiscardGraphics();
			}
		}catch( RuntimeException ex ){
			Die( ex );
		}
	}
	
	//***** Private *****
	
	private String toString( byte[] buf ){
		int n=buf.length;
		char tmp[]=new char[n];
		for( int i=0;i<n;++i ){
			tmp[i]=(char)(buf[i] & 0xff);
		}
		return new String( tmp );
	}
	
	private String loadString( byte[] buf ){
	
		int n=buf.length;
		StringBuilder out=new StringBuilder();
		
		int t0=n>0 ? buf[0] & 0xff : -1;
		int t1=n>1 ? buf[1] & 0xff : -1;
		
		if( t0==0xfe && t1==0xff ){
			int i=2;
			while( i<n-1 ){
				int x=buf[i++] & 0xff;
				int y=buf[i++] & 0xff;
				out.append( (char)((x<<8)|y) ); 
			}
		}else if( t0==0xff && t1==0xfe ){
			int i=2;
			while( i<n-1 ){
				int x=buf[i++] & 0xff;
				int y=buf[i++] & 0xff;
				out.append( (char)((y<<8)|x) ); 
			}
		}else{
			int t2=n>2 ? buf[2] & 0xff : -1;
			int i=(t0==0xef && t1==0xbb && t2==0xbf) ? 3 : 0;
			boolean fail=false;
			while( i<n ){
				int c=buf[i++] & 0xff;
				if( (c & 0x80)!=0 ){
					if( (c & 0xe0)==0xc0 ){
						if( i>=n || (buf[i] & 0xc0)!=0x80 ){
							fail=true;
							break;
						}
						c=((c & 0x1f)<<6) | (buf[i] & 0x3f);
						i+=1;
					}else if( (c & 0xf0)==0xe0 ){
						if( i+1>=n || (buf[i] & 0xc0)!=0x80 || (buf[i+1] & 0xc0)!=0x80 ){
							fail=true;
							break;
						}
						c=((c & 0x0f)<<12) | ((buf[i] & 0x3f)<<6) | (buf[i+1] & 0x3f);
						i+=2;
					}else{
						fail=true;
						break;
					}
				}
				out.append( (char)c );
			}
			if( fail ){
				return toString( buf );
			}
		}
		return out.toString();
	}
}





class ActivityDelegate{

	public void onStart(){
	}
	public void onRestart(){
	}
	public void onResume(){
	}
	public void onPause(){
	}
	public void onStop(){
	}
	public void onDestroy(){
	}
	public void onActivityResult( int requestCode,int resultCode,Intent data ){
	}
	public void onNewIntent( Intent intent ){
	}
}

class BBAndroidGame extends BBGame implements GLSurfaceView.Renderer,SensorEventListener{

	static BBAndroidGame _androidGame;
	
	Activity _activity;
	GameView _view;
	
	List<ActivityDelegate> _activityDelegates=new LinkedList<ActivityDelegate>();
	
	int _reqCode;
	
	Display _display;
	
	long _nextUpdate;
	
	long _updatePeriod;
	
	boolean _canRender;
	
	float[] _joyx=new float[2];
	float[] _joyy=new float[2];
	float[] _joyz=new float[2];
	boolean[] _buttons=new boolean[32];
	
	public BBAndroidGame( Activity activity,GameView view ){
		_androidGame=this;

		_activity=activity;
		_view=view;
		
		_display=_activity.getWindowManager().getDefaultDisplay();
		
		System.setOut( new PrintStream( new LogTool() ) );
	}
	
	public static BBAndroidGame AndroidGame(){

		return _androidGame;
	}
	
	//***** LogTool ******	

	static class LogTool extends OutputStream{
	
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		@Override
		public void write( int b ) throws IOException{
			if( b==(int)'\n' ){
				Log.i( "[Monkey]",new String( this.out.toByteArray() ) );
				this.out=new ByteArrayOutputStream();
			}else{
				this.out.write(b);
			}
		}
	}
	
	void ValidateUpdateTimer(){
		_nextUpdate=0;
		_updatePeriod=0;
		if( _updateRate!=0 ) _updatePeriod=1000000000/_updateRate;
		
	}
	
	//***** GameView *****
	
	public static class GameView extends GLSurfaceView{
	
		Object args1[]=new Object[1];
		float[] _touchX=new float[32];
		float[] _touchY=new float[32];

		boolean _useMulti;
		Method _getPointerCount,_getPointerId,_getX,_getY;
		
		boolean _useGamepad;
		Method _getSource,_getAxisValue;

		void init(){
		
			//get multi-touch methods
			try{
				Class cls=Class.forName( "android.view.MotionEvent" );
				Class intClass[]=new Class[]{ Integer.TYPE };
				_getPointerCount=cls.getMethod( "getPointerCount" );
				_getPointerId=cls.getMethod( "getPointerId",intClass );
				_getX=cls.getMethod( "getX",intClass );
				_getY=cls.getMethod( "getY",intClass );
				_useMulti=true;
			}catch( Exception ex ){
			}
			
			if( MonkeyConfig.ANDROID_GAMEPAD_ENABLED.equals( "1" ) ){
				try{
					//get gamepad methods
					Class cls=Class.forName( "android.view.MotionEvent" );
					Class intClass[]=new Class[]{ Integer.TYPE };
					_getSource=cls.getMethod( "getSource" );
					_getAxisValue=cls.getMethod( "getAxisValue",intClass );
					_useGamepad=true;
				}catch( Exception ex ){
				}
			}
		}

		public GameView( Context context ){
			super( context );
			init();
		}
		
		public GameView( Context context,AttributeSet attrs ){
			super( context,attrs );
			init();
		}
		
		public InputConnection onCreateInputConnection( EditorInfo outAttrs ){
			//voodoo to disable various undesirable soft keyboard features such as predictive text and fullscreen mode.
			outAttrs.inputType=InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD|InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;
			outAttrs.imeOptions=EditorInfo.IME_FLAG_NO_FULLSCREEN|EditorInfo.IME_FLAG_NO_EXTRACT_UI;			
			return null;
		}
		
		//View event handling
		public boolean dispatchKeyEventPreIme( KeyEvent event ){

			if( _useGamepad ){
				int button=-1;
				switch( event.getKeyCode() ){
				case 96: button=0;break;	//A
				case 97: button=1;break;	//B
				case 99: button=2;break;	//X
				case 100:button=3;break;	//Y
				case 102:button=4;break;	//LB
				case 103:button=5;break;	//RB
				case 108:button=7;break;	//START
				case 21: button=8;break;	//LEFT
				case 19: button=9;break;	//UP
				case 22: button=10;break;	//RIGHT
				case 20: button=11;break;	//DOWN
				}
				if( button!=-1 ){
					_androidGame._buttons[button]=(event.getAction()==KeyEvent.ACTION_DOWN);
					return true;
				}
			}
			
			//Convert back button to ESC in soft keyboard mode...
			if( _androidGame._keyboardEnabled ){
				if( event.getKeyCode()==KeyEvent.KEYCODE_BACK ){
					if( event.getAction()==KeyEvent.ACTION_DOWN ){
						_androidGame.KeyEvent( BBGameEvent.KeyChar,27 );
					}
					return true;
				}
			}
			return false;
		}
		
		public boolean onKeyDown( int key,KeyEvent event ){
		
			int vkey=-1;
			switch( event.getKeyCode() ){
			case KeyEvent.KEYCODE_MENU:vkey=0x1a1;break;
			case KeyEvent.KEYCODE_SEARCH:vkey=0x1a3;break;
			}
			if( vkey!=-1 ){
				_androidGame.KeyEvent( BBGameEvent.KeyDown,vkey );
				_androidGame.KeyEvent( BBGameEvent.KeyUp,vkey );
				return true;
			}
			
			if( !_androidGame._keyboardEnabled ) return false;
			
			if( event.getKeyCode()==KeyEvent.KEYCODE_DEL ){
				_androidGame.KeyEvent( BBGameEvent.KeyChar,8 );
			}else{
				int chr=event.getUnicodeChar();
				if( chr!=0 ){
					_androidGame.KeyEvent( BBGameEvent.KeyChar,chr==10 ? 13 : chr );
				}
			}
			return true;
		}
		
		public boolean onKeyMultiple( int keyCode,int repeatCount,KeyEvent event ){
			if( !_androidGame._keyboardEnabled ) return false;
		
			String str=event.getCharacters();
			for( int i=0;i<str.length();++i ){
				int chr=str.charAt( i );
				if( chr!=0 ){
					_androidGame.KeyEvent( BBGameEvent.KeyChar,chr==10 ? 13 : chr );
				}
			}
			return true;
		}
		
		public boolean onTouchEvent( MotionEvent event ){
		
			if( !_useMulti ){
				//mono-touch version...
				//
				switch( event.getAction() ){
				case MotionEvent.ACTION_DOWN:
					_androidGame.TouchEvent( BBGameEvent.TouchDown,0,event.getX(),event.getY() );
					break;
				case MotionEvent.ACTION_UP:
					_androidGame.TouchEvent( BBGameEvent.TouchUp,0,event.getX(),event.getY() );
					break;
				case MotionEvent.ACTION_MOVE:
					_androidGame.TouchEvent( BBGameEvent.TouchMove,0,event.getX(),event.getY() );
					break;
				}
				return true;
			}
	
			try{
	
				//multi-touch version...
				//
				final int ACTION_DOWN=0;
				final int ACTION_UP=1;
				final int ACTION_POINTER_DOWN=5;
				final int ACTION_POINTER_UP=6;
				final int ACTION_POINTER_INDEX_SHIFT=8;
				final int ACTION_MASK=255;
				
				int index=-1;
				int action=event.getAction();
				int masked=action & ACTION_MASK;
				
				if( masked==ACTION_DOWN || masked==ACTION_POINTER_DOWN || masked==ACTION_UP || masked==ACTION_POINTER_UP ){
	
					index=action>>ACTION_POINTER_INDEX_SHIFT;
					
					args1[0]=Integer.valueOf( index );
					int pid=((Integer)_getPointerId.invoke( event,args1 )).intValue();
	
					float x=_touchX[pid]=((Float)_getX.invoke( event,args1 )).floatValue();
					float y=_touchY[pid]=((Float)_getY.invoke( event,args1 )).floatValue();
					
					if( masked==ACTION_DOWN || masked==ACTION_POINTER_DOWN ){
						_androidGame.TouchEvent( BBGameEvent.TouchDown,pid,x,y );
					}else{
						_androidGame.TouchEvent( BBGameEvent.TouchUp,pid,x,y );
					}
				}
	
				int pointerCount=((Integer)_getPointerCount.invoke( event )).intValue();
			
				for( int i=0;i<pointerCount;++i ){
					if( i==index ) continue;
	
					args1[0]=Integer.valueOf( i );
					int pid=((Integer)_getPointerId.invoke( event,args1 )).intValue();
	
					float x=((Float)_getX.invoke( event,args1 )).floatValue();
					float y=((Float)_getY.invoke( event,args1 )).floatValue();
	
					if( x!=_touchX[pid] || y!=_touchY[pid] ){
						_touchX[pid]=x;
						_touchY[pid]=y;
						_androidGame.TouchEvent( BBGameEvent.TouchMove,pid,x,y );
					}
				}
			}catch( Exception ex ){
			}
	
			return true;
		}
		
		//New! Dodgy gamepad support...
		public boolean onGenericMotionEvent( MotionEvent event ){
		
			if( !_useGamepad ) return false;
			
			try{
				int source=((Integer)_getSource.invoke( event )).intValue();

				if( (source&16)==0 ) return false;
			
				BBAndroidGame g=_androidGame;
			
				args1[0]=Integer.valueOf( 0  );g._joyx[0]=((Float)_getAxisValue.invoke( event,args1 )).floatValue();
				args1[0]=Integer.valueOf( 1  );g._joyy[0]=((Float)_getAxisValue.invoke( event,args1 )).floatValue();
				args1[0]=Integer.valueOf( 17 );g._joyz[0]=((Float)_getAxisValue.invoke( event,args1 )).floatValue();
				
				args1[0]=Integer.valueOf( 11 );g._joyx[1]=((Float)_getAxisValue.invoke( event,args1 )).floatValue();
				args1[0]=Integer.valueOf( 14 );g._joyy[1]=((Float)_getAxisValue.invoke( event,args1 )).floatValue();
				args1[0]=Integer.valueOf( 18 );g._joyz[1]=((Float)_getAxisValue.invoke( event,args1 )).floatValue();
				
				return true;
				
			}catch( Exception ex ){
			}

			return false;
		}
	}
	
	//***** BBGame ******
	
	public int GetDeviceWidth(){
		return _view.getWidth();
	}
	
	public int GetDeviceHeight(){
		return _view.getHeight();
	}
	
	public void SetKeyboardEnabled( boolean enabled ){
		super.SetKeyboardEnabled( enabled );

		InputMethodManager mgr=(InputMethodManager)_activity.getSystemService( Context.INPUT_METHOD_SERVICE );
		
		if( _keyboardEnabled ){
			// Hack for someone's phone...My LG or Samsung don't need it...
			mgr.hideSoftInputFromWindow( _view.getWindowToken(),0 );
			mgr.showSoftInput( _view,0 );		//0 is 'magic'! InputMethodManager.SHOW_IMPLICIT does weird things...
		}else{
			mgr.hideSoftInputFromWindow( _view.getWindowToken(),0 );
		}
	}
	
	public void SetUpdateRate( int hertz ){
		super.SetUpdateRate( hertz );
		ValidateUpdateTimer();
	}	

	public int SaveState( String state ){
		SharedPreferences prefs=_activity.getPreferences( 0 );
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString( ".monkeystate",state );
		editor.commit();
		return 1;
	}
	
	public String LoadState(){
		SharedPreferences prefs=_activity.getPreferences( 0 );
		String state=prefs.getString( ".monkeystate","" );
		if( state.equals( "" ) ) state=prefs.getString( "gxtkAppState","" );
		return state;
	}
	
	static public String LoadState_V66b(){
		SharedPreferences prefs=_androidGame._activity.getPreferences( 0 );
		return prefs.getString( "gxtkAppState","" );
	}
	
	static public void SaveState_V66b( String state ){
		SharedPreferences prefs=_androidGame._activity.getPreferences( 0 );
		SharedPreferences.Editor editor=prefs.edit();
		editor.putString( "gxtkAppState",state );
		editor.commit();
	}
	
	public boolean PollJoystick( int port,float[] joyx,float[] joyy,float[] joyz,boolean[] buttons ){
		if( port!=0 ) return false;
		joyx[0]=_joyx[0];joyy[0]=_joyy[0];joyz[0]=_joyz[0];
		joyx[1]=_joyx[1];joyy[1]=_joyy[1];joyz[1]=_joyz[1];
		for( int i=0;i<32;++i ) buttons[i]=_buttons[i];
		return true;
	}
	
	public void OpenUrl( String url ){
		Intent browserIntent=new Intent( Intent.ACTION_VIEW,android.net.Uri.parse( url ) );
		_activity.startActivity( browserIntent );
	}
	
	String PathToFilePath( String path ){
		if( !path.startsWith( "monkey://" ) ){
			return path;
		}else if( path.startsWith( "monkey://internal/" ) ){
			File f=_activity.getFilesDir();
			if( f!=null ) return f+"/"+path.substring(18);
		}else if( path.startsWith( "monkey://external/" ) ){
			File f=Environment.getExternalStorageDirectory();
			if( f!=null ) return f+"/"+path.substring(18);
		}
		return "";
	}

	String PathToAssetPath( String path ){
		if( path.startsWith( "monkey://data/" ) ) return "monkey/"+path.substring(14);
		return "";
	}

	public InputStream OpenInputStream( String path ){
		if( !path.startsWith( "monkey://data/" ) ) return super.OpenInputStream( path );
		try{
			return _activity.getAssets().open( PathToAssetPath( path ) );
		}catch( IOException ex ){
		}
		return null;
	}

	public Activity GetActivity(){
		return _activity;
	}

	public GameView GetGameView(){
		return _view;
	}
	
	public void AddActivityDelegate( ActivityDelegate delegate ){
		if( _activityDelegates.contains( delegate ) ) return;
		_activityDelegates.add( delegate );
	}
	
	public int AllocateActivityResultRequestCode(){
		return ++_reqCode;
	}
	
	public void RemoveActivityDelegate( ActivityDelegate delegate ){
		_activityDelegates.remove( delegate );
	}

	public Bitmap LoadBitmap( String path ){
		try{
			InputStream in=OpenInputStream( path );
			if( in==null ) return null;

			BitmapFactory.Options opts=new BitmapFactory.Options();
			opts.inPreferredConfig=Bitmap.Config.ARGB_8888;
			opts.inPurgeable=true;

			Bitmap bitmap=BitmapFactory.decodeStream( in,null,opts );
			in.close();
			
			return bitmap;
		}catch( IOException e ){
		}
		return null;
	}

	public int LoadSound( String path,SoundPool pool ){
		try{
			if( path.startsWith( "monkey://data/" ) ){
				return pool.load( _activity.getAssets().openFd( PathToAssetPath( path ) ),1 );
			}else{
				return pool.load( PathToFilePath( path ),1 );
			}
		}catch( IOException ex ){
		}
		return 0;
	}
	
	public MediaPlayer OpenMedia( String path ){
		try{
			MediaPlayer mp;
			
			if( path.startsWith( "monkey://data/" ) ){
				AssetFileDescriptor fd=_activity.getAssets().openFd( PathToAssetPath( path ) );
				mp=new MediaPlayer();
				mp.setDataSource( fd.getFileDescriptor(),fd.getStartOffset(),fd.getLength() );
				mp.prepare();
				fd.close();
			}else{
				mp=new MediaPlayer();
				mp.setDataSource( PathToFilePath( path ) );
				mp.prepare();
			}
			return mp;
			
		}catch( IOException ex ){
		}
		return null;
	}
	
	//***** INTERNAL *****
	
	public void SuspendGame(){
		super.SuspendGame();
		ValidateUpdateTimer();
		_canRender=false;
	}
	
	public void ResumeGame(){
		super.ResumeGame();
		ValidateUpdateTimer();
	}

	public void UpdateGame(){
		//
		//Ok, this isn't very polite - if keyboard enabled, we just thrash showSoftInput.
		//
		//But showSoftInput doesn't seem to be too reliable - esp. after onResume - and I haven't found a way to
		//determine if keyboard is showing, so what can yer do...
		//
		if( _keyboardEnabled ){
			InputMethodManager mgr=(InputMethodManager)_activity.getSystemService( Context.INPUT_METHOD_SERVICE );
			mgr.showSoftInput( _view,0 );		//0 is 'magic'! InputMethodManager.SHOW_IMPLICIT does weird things...
		}
		super.UpdateGame();
	}
	
	public void Run(){

		//touch input handling	
		SensorManager sensorManager=(SensorManager)_activity.getSystemService( Context.SENSOR_SERVICE );
		List<Sensor> sensorList=sensorManager.getSensorList( Sensor.TYPE_ACCELEROMETER );
		Iterator<Sensor> it=sensorList.iterator();
		if( it.hasNext() ){
			Sensor sensor=it.next();
			sensorManager.registerListener( this,sensor,SensorManager.SENSOR_DELAY_GAME );
		}
		
		//audio volume control
		_activity.setVolumeControlStream( AudioManager.STREAM_MUSIC );

		//GL version
		if( MonkeyConfig.OPENGL_GLES20_ENABLED.equals( "1" ) ){
			//
			//_view.setEGLContextClientVersion( 2 );	//API 8 only!
			//
			try{
				Class clas=_view.getClass();
				Class parms[]=new Class[]{ Integer.TYPE };
				Method setVersion=clas.getMethod( "setEGLContextClientVersion",parms );
				Object args[]=new Object[1];
				args[0]=Integer.valueOf( 2 );
				setVersion.invoke( _view,args );
			}catch( Exception ex ){
			}
		}

		_view.setRenderer( this );
		_view.setFocusableInTouchMode( true );
		_view.requestFocus();
	}
	
	//***** GLSurfaceView.Renderer *****

	public void onDrawFrame( GL10 gl ){
		if( !_canRender ) return;
		
		StartGame();
		
		if( _updateRate==0 ){
			UpdateGame();
			RenderGame();
			return;
		}
			
		if( _nextUpdate==0 ){
			_nextUpdate=System.nanoTime();
		}else{
			long delay=_nextUpdate-System.nanoTime();
			if( delay>0 ){
				try{
					Thread.sleep( delay/1000000 );
				}catch( InterruptedException e ){
					_nextUpdate=0;
				}
			}
		}
			
		int i=0;
		for( ;i<4;++i ){
		
			UpdateGame();
			if( _nextUpdate==0 ) break;
			
			_nextUpdate+=_updatePeriod;
			if( _nextUpdate>System.nanoTime() ) break;
		}
		if( i==4 ) _nextUpdate=0;
		
		RenderGame();		
	}
	
	public void onSurfaceChanged( GL10 gl,int width,int height ){
	}
	
	public void onSurfaceCreated( GL10 gl,EGLConfig config ){
		_canRender=true;
		DiscardGraphics();
	}
	
	//***** SensorEventListener *****
	
	public void onAccuracyChanged( Sensor sensor,int accuracy ){
	}
	
	public void onSensorChanged( SensorEvent event ){
		Sensor sensor=event.sensor;
		float x,y,z;
		switch( sensor.getType() ){
		case Sensor.TYPE_ORIENTATION:
			break;
		case Sensor.TYPE_ACCELEROMETER:
//			switch( _display.getRotation() ){
			switch( _display.getOrientation() ){	//deprecated in API 8, but we support 3...
			case Surface.ROTATION_0:
				x=event.values[0]/-9.81f;
				y=event.values[1]/9.81f;
				break;
			case Surface.ROTATION_90:
				x=event.values[1]/9.81f;
				y=event.values[0]/9.81f;
				break;
			case Surface.ROTATION_180:
				x=event.values[0]/9.81f;
				y=event.values[1]/-9.81f;
				break;
			case Surface.ROTATION_270:
				x=event.values[1]/-9.81f;
				y=event.values[0]/-9.81f;
				break;
			default:
				x=event.values[0]/-9.81f;
				y=event.values[1]/9.81f;
				break;
			}
			z=event.values[2]/-9.81f;
			MotionEvent( BBGameEvent.MotionAccel,-1,x,y,z );
			break;
		}
	}
}

class AndroidGame extends Activity{

	BBAndroidGame _game;
	
	GameView _view;
	
	//***** GameView *****

	public static class GameView extends BBAndroidGame.GameView{

		public GameView( Context context ){
			super( context );
		}
		
		public GameView( Context context,AttributeSet attrs ){
			super( context,attrs );
		}
	}
	
	//***** Activity *****
	public void onWindowFocusChanged( boolean hasFocus ){
		if( hasFocus ){
			_view.onResume();
			_game.ResumeGame();
		}else{
			_game.SuspendGame();
			_view.onPause();
		}
	}

	@Override
	public void onBackPressed(){
		//deprecating this!
		_game.KeyEvent( BBGameEvent.KeyDown,27 );
		_game.KeyEvent( BBGameEvent.KeyUp,27 );
		
		//new KEY_BACK value...
		_game.KeyEvent( BBGameEvent.KeyDown,0x1a0 );
		_game.KeyEvent( BBGameEvent.KeyUp,0x1a0 );
	}

	@Override
	public void onStart(){
		super.onResume();
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onStart();
		}
	}
	
	@Override
	public void onRestart(){
		super.onResume();
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onRestart();
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onResume();
		}
	}
	
	@Override 
	public void onPause(){
		super.onPause();
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onPause();
		}
	}

	@Override
	public void onStop(){
		super.onResume();
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onStop();
		}
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onDestroy();
		}
	}
	
	@Override
	protected void onActivityResult( int requestCode,int resultCode,Intent data ){
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onActivityResult( requestCode,resultCode,data );
		}
	}
	
	@Override
	public void onNewIntent( Intent intent ){
		super.onNewIntent( intent );
		for( ActivityDelegate delegate : _game._activityDelegates ){
			delegate.onNewIntent( intent );
		}
	}
}


class BBMonkeyGame extends BBAndroidGame{

	public BBMonkeyGame( AndroidGame game,AndroidGame.GameView view ){
		super( game,view );
	}
}

public class MonkeyGame extends AndroidGame{

	public static class GameView extends AndroidGame.GameView{

		public GameView( Context context ){
			super( context );
		}
		
		public GameView( Context context,AttributeSet attrs ){
			super( context,attrs );
		}
	}
	
	@Override
	public void onCreate( Bundle savedInstanceState ){
		super.onCreate( savedInstanceState );
		
		setContentView( R.layout.main );
		
		_view=(GameView)findViewById( R.id.gameView );
		
		_game=new BBMonkeyGame( this,_view );
		
		try{
				
			bb_.bbInit();
			bb_.bbMain();
			
		}catch( RuntimeException ex ){

			_game.Die( ex );

			finish();
		}

		if( _game.Delegate()==null ) finish();
		
		_game.Run();
	}
};


// Android mojo runtime.
//
// Copyright 2011 Mark Sibly, all rights reserved.
// No warranty implied; use at your own risk.






class gxtkGraphics{

	static class RenderOp{
		int type,count,alpha;
		gxtkSurface surf;
	};

	static final int MAX_VERTICES=65536/20;
	static final int MAX_RENDEROPS=MAX_VERTICES/2;
	static final int MAX_QUAD_INDICES=MAX_VERTICES/4*6;
	
	static int seq=1;
	
	BBAndroidGame game;
	
	boolean gles20;
	int width,height;
	
	float alpha;
	float r,g,b;
	int colorARGB;
	int blend;
	float ix,iy,jx,jy,tx,ty;
	boolean tformed;
	
	RenderOp renderOps[]=new RenderOp[MAX_RENDEROPS];
	RenderOp rop,nullRop;
	int nextOp,vcount;

	float[] vertices=new float[MAX_VERTICES*4];	//x,y,u,v
	int[] colors=new int[MAX_VERTICES];	//rgba
	int vp,cp;
	
	FloatBuffer vbuffer;
	IntBuffer cbuffer;
	int vbo,vbo_seq,ibo;
	
	gxtkGraphics(){
	
		game=BBAndroidGame.AndroidGame();
		
		width=game.GetGameView().getWidth();
		height=game.GetGameView().getHeight();
		
		gles20=MonkeyConfig.OPENGL_GLES20_ENABLED.equals( "1" );
		if( gles20 ) return;
	
		for( int i=0;i<MAX_RENDEROPS;++i ){
			renderOps[i]=new RenderOp();
		}
		nullRop=new RenderOp();
		nullRop.type=-1;

		vbuffer=FloatBuffer.wrap( vertices,0,MAX_VERTICES*4 );
		cbuffer=IntBuffer.wrap( colors,0,MAX_VERTICES );
	}
	
	void Reset(){
		rop=nullRop;
		nextOp=0;
		vcount=0;
	}

	void Flush(){
		if( vcount==0 ) return;
		
		//'discard' buffer contents...
		GLES11.glBufferData( GLES11.GL_ARRAY_BUFFER,MAX_VERTICES*20,null,GLES11.GL_DYNAMIC_DRAW );
		
		GLES11.glBufferSubData( GLES11.GL_ARRAY_BUFFER,0,vcount*16,vbuffer );
		GLES11.glBufferSubData( GLES11.GL_ARRAY_BUFFER,vcount*16,vcount*4,cbuffer );
		GLES11.glColorPointer( 4,GLES11.GL_UNSIGNED_BYTE,0,vcount*16 );

		GLES11.glDisable( GLES11.GL_TEXTURE_2D );
		GLES11.glDisable( GLES11.GL_BLEND );

		int index=0;
		boolean blendon=false;
		gxtkSurface surf=null;

		for( int i=0;i<nextOp;++i ){

			RenderOp op=renderOps[i];
			
			if( op.surf!=null ){
				if( op.surf!=surf ){
					if( surf==null ) GLES11.glEnable( GLES11.GL_TEXTURE_2D );
					surf=op.surf;
					surf.Bind();
				}
			}else{
				if( surf!=null ){
					GLES11.glDisable( GLES11.GL_TEXTURE_2D );
					surf=null;
				}
			}
			
			//should just have another blend mode...
			if( blend==1 || (op.alpha>>>24)!=0xff || (op.surf!=null && op.surf.hasAlpha) ){
				if( !blendon ){
					GLES11.glEnable( GLES11.GL_BLEND );
					blendon=true;
				}
			}else{
				if( blendon ){
					GLES11.glDisable( GLES11.GL_BLEND );
					blendon=false;
				}
			}
			
			switch( op.type ){
			case 1:
				GLES11.glDrawArrays( GLES11.GL_POINTS,index,op.count );
				break;
			case 2:
				GLES11.glDrawArrays( GLES11.GL_LINES,index,op.count );
				break;
			case 3:
				GLES11.glDrawArrays( GLES11.GL_TRIANGLES,index,op.count );
				break;
			case 4:
				GLES11.glDrawElements( GLES11.GL_TRIANGLES,op.count/4*6,GLES11.GL_UNSIGNED_SHORT,(index/4*6+(index&3)*MAX_QUAD_INDICES)*2 );
				break;
			default:
				for( int j=0;j<op.count;j+=op.type ){
					GLES11.glDrawArrays( GLES11.GL_TRIANGLE_FAN,index+j,op.type );
				}
			}
			
			index+=op.count;
		}
		
		Reset();
	}
	
	void Begin( int type,int count,gxtkSurface surf ){
		if( vcount+count>MAX_VERTICES ){
			Flush();
		}
		if( type!=rop.type || surf!=rop.surf ){
			if( nextOp==MAX_RENDEROPS ) Flush();
			rop=renderOps[nextOp];
			nextOp+=1;
			rop.type=type;
			rop.surf=surf;
			rop.count=0;
			rop.alpha=~0;
		}
		rop.alpha&=colorARGB;
		rop.count+=count;
		vp=vcount*4;
		cp=vcount;
		vcount+=count;
	}

	//***** GXTK API *****

	int Width(){
		return width;
	}
	
	int Height(){
		return height;
	}
	
	int BeginRender(){

		width=game.GetGameView().getWidth();
		height=game.GetGameView().getHeight();
		
		if( gles20 ) return 0;
		
		if( vbo_seq!=seq ){

			vbo_seq=seq;
			
			int[] bufs=new int[2];
			GLES11.glGenBuffers( 2,bufs,0 );
			vbo=bufs[0];
			ibo=bufs[1];
			
			GLES11.glBindBuffer( GLES11.GL_ARRAY_BUFFER,vbo );
			GLES11.glBufferData( GLES11.GL_ARRAY_BUFFER,MAX_VERTICES*20,null,GLES11.GL_DYNAMIC_DRAW );
			
			short[] idxs=new short[MAX_QUAD_INDICES*4];
			for( int j=0;j<4;++j ){
				int k=j*MAX_QUAD_INDICES;
				for( int i=0;i<MAX_QUAD_INDICES/6;++i ){
					idxs[i*6+k+0]=(short)(i*4+j);
					idxs[i*6+k+1]=(short)(i*4+j+1);
					idxs[i*6+k+2]=(short)(i*4+j+2);
					idxs[i*6+k+3]=(short)(i*4+j);
					idxs[i*6+k+4]=(short)(i*4+j+2);
					idxs[i*6+k+5]=(short)(i*4+j+3);
				}
			}
			ShortBuffer ibuffer=ShortBuffer.wrap( idxs,0,idxs.length );
			GLES11.glBindBuffer( GLES11.GL_ELEMENT_ARRAY_BUFFER,ibo );
			GLES11.glBufferData( GLES11.GL_ELEMENT_ARRAY_BUFFER,idxs.length*2,ibuffer,GLES11.GL_STATIC_DRAW );
		}
		
		GLES11.glViewport( 0,0,Width(),Height() );
		
		GLES11.glMatrixMode( GLES11.GL_PROJECTION );
		GLES11.glLoadIdentity();
		GLES11.glOrthof( 0,Width(),Height(),0,-1,1 );
		
		GLES11.glMatrixMode( GLES11.GL_MODELVIEW );
		GLES11.glLoadIdentity();
		
		GLES11.glEnable( GLES11.GL_BLEND );
		GLES11.glBlendFunc( GLES11.GL_ONE,GLES11.GL_ONE_MINUS_SRC_ALPHA );

		GLES11.glBindBuffer( GLES11.GL_ARRAY_BUFFER,vbo );
		GLES11.glBindBuffer( GLES11.GL_ELEMENT_ARRAY_BUFFER,ibo );
		GLES11.glEnableClientState( GLES11.GL_VERTEX_ARRAY );
		GLES11.glEnableClientState( GLES11.GL_TEXTURE_COORD_ARRAY );
		GLES11.glEnableClientState( GLES11.GL_COLOR_ARRAY );
		GLES11.glVertexPointer( 2,GLES11.GL_FLOAT,16,0 );
		GLES11.glTexCoordPointer( 2,GLES11.GL_FLOAT,16,8 );
		GLES11.glColorPointer( 4,GLES11.GL_UNSIGNED_BYTE,0,MAX_VERTICES*16 );

		Reset();
		
		return 1;
	}
	
	void EndRender(){
		if( gles20 ) return;
		Flush();
	}
	
	boolean LoadSurface__UNSAFE__( gxtkSurface surface,String path ){
		Bitmap bitmap=game.LoadBitmap( path );
		if( bitmap==null ) return false;
		surface.SetBitmap( bitmap );
		return true;
	}
	
	gxtkSurface LoadSurface( String path ){
		gxtkSurface surf=new gxtkSurface();
		if( !LoadSurface__UNSAFE__( surf,path ) ) return null;
		return surf;
	}
	
	gxtkSurface CreateSurface( int width,int height ){
		Bitmap bitmap=Bitmap.createBitmap( width,height,Bitmap.Config.ARGB_8888 );
		if( bitmap!=null ) return new gxtkSurface( bitmap );
		return null;
	}
	
	void DiscardGraphics(){
		gxtkSurface.FlushDiscarded( false );
		seq+=1;
	}

	int SetAlpha( float alpha ){
		this.alpha=alpha;
		int a=(int)(alpha*255);
		colorARGB=(a<<24) | ((int)(b*alpha)<<16) | ((int)(g*alpha)<<8) | (int)(r*alpha);
		return 0;
	}

	int SetColor( float r,float g,float b ){
		this.r=r;
		this.g=g;
		this.b=b;
		int a=(int)(alpha*255);
		colorARGB=(a<<24) | ((int)(b*alpha)<<16) | ((int)(g*alpha)<<8) | (int)(r*alpha);
		return 0;
	}
	
	int SetBlend( int blend ){
		if( blend==this.blend ) return 0;
		
		Flush();
		
		this.blend=blend;
		
		switch( blend ){
		case 1:
			GLES11.glBlendFunc( GLES11.GL_ONE,GLES11.GL_ONE );
			break;
		default:
			GLES11.glBlendFunc( GLES11.GL_ONE,GLES11.GL_ONE_MINUS_SRC_ALPHA );
		}
		return 0;
	}
	
	int SetScissor( int x,int y,int w,int h ){
		Flush();
		
		if( x!=0 || y!=0 || w!=Width() || h!=Height() ){
			GLES11.glEnable( GLES11.GL_SCISSOR_TEST );
			y=Height()-y-h;
			GLES11.glScissor( x,y,w,h );
		}else{
			GLES11.glDisable( GLES11.GL_SCISSOR_TEST );
		}
		return 0;
	}
	
	int SetMatrix( float ix,float iy,float jx,float jy,float tx,float ty ){
	
		tformed=(ix!=1 || iy!=0 || jx!=0 || jy!=1 || tx!=0 || ty!=0);
		this.ix=ix;
		this.iy=iy;
		this.jx=jx;
		this.jy=jy;
		this.tx=tx;
		this.ty=ty;
		
		return 0;
	}
	
	int Cls( float r,float g,float b ){
		Reset();
		
		GLES11.glClearColor( r/255.0f,g/255.0f,b/255.0f,1 );
		GLES11.glClear( GLES11.GL_COLOR_BUFFER_BIT );	//|GLES11.GL_DEPTH_BUFFER_BIT ); //GL_DEPTH_BUFFER_BIT crashes someone's phone...
		
		return 0;
	}
	
	int DrawPoint( float x,float y ){
	
		if( tformed ){
			float px=x;
			x=px * ix + y * jx + tx;
			y=px * iy + y * jy + ty;
		}
		
		Begin( 1,1,null );
		
		vertices[vp]=x+.5f;vertices[vp+1]=y+.5f;
		
		colors[cp]=colorARGB;
		
		return 0;
	}
	
	int DrawLine( float x0,float y0,float x1,float y1 ){
		
		if( tformed ){
			float tx0=x0,tx1=x1;
			x0=tx0 * ix + y0 * jx + tx;
			y0=tx0 * iy + y0 * jy + ty;
			x1=tx1 * ix + y1 * jx + tx;
			y1=tx1 * iy + y1 * jy + ty;
		}

		Begin( 2,2,null );

		vertices[vp]=x0+.5f;vertices[vp+1]=y0+.5f;
		vertices[vp+4]=x1+.5f;vertices[vp+5]=y1+.5f;

		colors[cp]=colors[cp+1]=colorARGB;

		return 0;
 	}

	int DrawRect( float x,float y,float w,float h ){
	
		float x0=x,x1=x+w,x2=x+w,x3=x;
		float y0=y,y1=y,y2=y+h,y3=y+h;
		
		if( tformed ){
			float tx0=x0,tx1=x1,tx2=x2,tx3=x3;
			x0=tx0 * ix + y0 * jx + tx;
			y0=tx0 * iy + y0 * jy + ty;
			x1=tx1 * ix + y1 * jx + tx;
			y1=tx1 * iy + y1 * jy + ty;
			x2=tx2 * ix + y2 * jx + tx;
			y2=tx2 * iy + y2 * jy + ty;
			x3=tx3 * ix + y3 * jx + tx;
			y3=tx3 * iy + y3 * jy + ty;
		}

		Begin( 4,4,null );
		
		vertices[vp]=x0;vertices[vp+1]=y0;
		vertices[vp+4]=x1;vertices[vp+5]=y1;
		vertices[vp+8]=x2;vertices[vp+9]=y2;
		vertices[vp+12]=x3;vertices[vp+13]=y3;
		
		colors[cp]=colors[cp+1]=colors[cp+2]=colors[cp+3]=colorARGB;

		return 0;
	}
	
	int DrawOval( float x,float y,float w,float h ){

		float xr=w/2.0f;
		float yr=h/2.0f;

		int n;	
		if( tformed ){
			float xx=xr*ix,xy=xr*iy,xd=(float)Math.sqrt(xx*xx+xy*xy);
			float yx=yr*jx,yy=yr*jy,yd=(float)Math.sqrt(yx*yx+yy*yy);
			n=(int)( xd+yd );
		}else{
			n=(int)( Math.abs(xr)+Math.abs(yr) );
		}

		if( n>MAX_VERTICES ){
			n=MAX_VERTICES;
		}else if( n<12 ){
			n=12;
		}else{
			n&=~3;
		}
		
		x+=xr;
		y+=yr;
		
		Begin( n,n,null );
		
		for( int i=0;i<n;++i ){
			float th=i * 6.28318531f / n;
			float x0=(float)(x+Math.cos(th)*xr);
			float y0=(float)(y+Math.sin(th)*yr);
			if( tformed ){
				float tx0=x0;
				x0=tx0 * ix + y0 * jx + tx;
				y0=tx0 * iy + y0 * jy + ty;
			}
			vertices[vp]=x0;
			vertices[vp+1]=y0;
			colors[cp]=colorARGB;
			vp+=4;
			cp+=1;
		}
		
		return 0;
	}
	
	int DrawPoly( float[] verts ){
		if( verts.length<6 || verts.length>MAX_VERTICES*2 ) return 0;

		Begin( verts.length/2,verts.length/2,null );		
		
		if( tformed ){
			for( int i=0;i<verts.length;i+=2 ){
				vertices[vp  ]=verts[i] * ix + verts[i+1] * jx + tx;
				vertices[vp+1]=verts[i] * iy + verts[i+1] * jy + ty;
				colors[cp]=colorARGB;
				vp+=4;
				cp+=1;
			}
		}else{
			for( int i=0;i<verts.length;i+=2 ){
				vertices[vp  ]=verts[i];
				vertices[vp+1]=verts[i+1];
				colors[cp]=colorARGB;
				vp+=4;
				cp+=1;
			}
		}
		
		return 0;
	}
	
	int DrawPoly2( float[] verts,gxtkSurface surface,int srcx,int srcy ){
	
		int n=verts.length/4;
		if( n<1 || n>MAX_VERTICES ) return 0;
		
		Begin( n,n,surface );
		
		for( int i=0;i<n;++i ){
			int j=i*4;
			if( tformed ){
				vertices[vp  ]=verts[j] * ix + verts[j+1] * jx + tx;
				vertices[vp+1]=verts[j] * iy + verts[j+1] * jy + ty;
			}else{
				vertices[vp  ]=verts[j];
				vertices[vp+1]=verts[j+1];
			}
			vertices[vp+2]=(srcx+verts[j+2])*surface.uscale;
			vertices[vp+3]=(srcy+verts[j+3])*surface.vscale;
			colors[cp]=colorARGB;
			vp+=4;
			cp+=1;
		}
		
		return 0;
	}
	
	int DrawSurface( gxtkSurface surface,float x,float y ){
	
		float w=surface.width;
		float h=surface.height;
		float u0=0,u1=w*surface.uscale;
		float v0=0,v1=h*surface.vscale;
		
		float x0=x,x1=x+w,x2=x+w,x3=x;
		float y0=y,y1=y,y2=y+h,y3=y+h;
		
		if( tformed ){
			float tx0=x0,tx1=x1,tx2=x2,tx3=x3;
			x0=tx0 * ix + y0 * jx + tx;
			y0=tx0 * iy + y0 * jy + ty;
			x1=tx1 * ix + y1 * jx + tx;
			y1=tx1 * iy + y1 * jy + ty;
			x2=tx2 * ix + y2 * jx + tx;
			y2=tx2 * iy + y2 * jy + ty;
			x3=tx3 * ix + y3 * jx + tx;
			y3=tx3 * iy + y3 * jy + ty;
		}
	
		Begin( 4,4,surface );
		
		vertices[vp]=x0;vertices[vp+1]=y0;vertices[vp+2]=u0;vertices[vp+3]=v0;
		vertices[vp+4]=x1;vertices[vp+5]=y1;vertices[vp+6]=u1;vertices[vp+7]=v0;
		vertices[vp+8]=x2;vertices[vp+9]=y2;vertices[vp+10]=u1;vertices[vp+11]=v1;
		vertices[vp+12]=x3;vertices[vp+13]=y3;vertices[vp+14]=u0;vertices[vp+15]=v1;

		colors[cp]=colors[cp+1]=colors[cp+2]=colors[cp+3]=colorARGB;

		return 0;
	}
	
	int DrawSurface2( gxtkSurface surface,float x,float y,int srcx,int srcy,int srcw,int srch ){
	
		float w=srcw;
		float h=srch;
		float u0=srcx*surface.uscale,u1=(srcx+srcw)*surface.uscale;
		float v0=srcy*surface.vscale,v1=(srcy+srch)*surface.vscale;
		
		float x0=x,x1=x+w,x2=x+w,x3=x;
		float y0=y,y1=y,y2=y+h,y3=y+h;
		
		if( tformed ){
			float tx0=x0,tx1=x1,tx2=x2,tx3=x3;
			x0=tx0 * ix + y0 * jx + tx;
			y0=tx0 * iy + y0 * jy + ty;
			x1=tx1 * ix + y1 * jx + tx;
			y1=tx1 * iy + y1 * jy + ty;
			x2=tx2 * ix + y2 * jx + tx;
			y2=tx2 * iy + y2 * jy + ty;
			x3=tx3 * ix + y3 * jx + tx;
			y3=tx3 * iy + y3 * jy + ty;
		}

		Begin( 4,4,surface );
		
		vertices[vp]=x0;vertices[vp+1]=y0;vertices[vp+2]=u0;vertices[vp+3]=v0;
		vertices[vp+4]=x1;vertices[vp+5]=y1;vertices[vp+6]=u1;vertices[vp+7]=v0;
		vertices[vp+8]=x2;vertices[vp+9]=y2;vertices[vp+10]=u1;vertices[vp+11]=v1;
		vertices[vp+12]=x3;vertices[vp+13]=y3;vertices[vp+14]=u0;vertices[vp+15]=v1;

		colors[cp]=colors[cp+1]=colors[cp+2]=colors[cp+3]=colorARGB;

		return 0;
	}
	
	int ReadPixels( int[] pixels,int x,int y,int width,int height,int offset,int pitch ){
	
		Flush();
		
		int[] texels=new int[width*height];
		IntBuffer buf=IntBuffer.wrap( texels );

		GLES11.glReadPixels( x,Height()-y-height,width,height,GLES11.GL_RGBA,GLES11.GL_UNSIGNED_BYTE,buf );

		int i=0;
		for( int py=height-1;py>=0;--py ){
			int j=offset+py*pitch;
			for( int px=0;px<width;++px ){
				int p=texels[i++];
				//RGBA -> BGRA, Big Endian!
				pixels[j++]=(p&0xff000000)|((p<<16)&0xff0000)|(p&0xff00)|((p>>16)&0xff);
			}
		}
	
		return 0;
	}

	int WritePixels2( gxtkSurface surface,int[] pixels,int x,int y,int width,int height,int offset,int pitch ){
	
		surface.bitmap.setPixels( pixels,offset,pitch,x,y,width,height );
		
		surface.Invalidate();
	
		return 0;
	}
	
}

class gxtkSurface{

	Bitmap bitmap;
	
	int width,height;
	int twidth,theight;
	float uscale,vscale;
	boolean hasAlpha;
	int format,type;
	int texId,seq;

	static Vector discarded=new Vector();
	
	gxtkSurface(){
	}
	
	gxtkSurface( Bitmap bitmap ){
		SetBitmap( bitmap );
	}
	
	void SetBitmap( Bitmap bitmap ){
		this.bitmap=bitmap;
		width=bitmap.getWidth();
		height=bitmap.getHeight();
		hasAlpha=bitmap.hasAlpha();
		twidth=Pow2Size( width );
		theight=Pow2Size( height );
		uscale=1.0f/(float)twidth;
		vscale=1.0f/(float)theight;
	}

	protected void finalize(){
		Discard();
	}
	
	int Pow2Size( int n ){
		int i=1;
		while( i<n ) i*=2;
		return i;
	}
	
	static void FlushDiscarded( boolean deltexs ){
		if( deltexs ){
			int n=discarded.size();
			if( n>0 ){
				int[] texs=new int[n];
				for( int i=0;i<n;++i ){
					texs[i]=((Integer)discarded.elementAt(i)).intValue();
				}
				GLES11.glDeleteTextures( n,texs,0 );
			}
		}
		discarded.clear();
	}
	
	void Invalidate(){
		if( seq==gxtkGraphics.seq ){
			discarded.add( Integer.valueOf( texId ) );
			seq=0;
		}
	}

	//Experimental version...
	//
	void Bind2(){
	
		if( seq==gxtkGraphics.seq ){
			GLES11.glBindTexture( GLES11.GL_TEXTURE_2D,texId );
			return;
		}
        
        if( bitmap==null ) throw new Error( "Attempt to use discarded image" );
		
		FlushDiscarded( true );

		int[] texs=new int[1];
		GLES11.glGenTextures( 1,texs,0 );
		texId=texs[0];
		if( texId==0 ) throw new Error( "glGenTextures failed" );
		seq=gxtkGraphics.seq;
		
		GLES11.glBindTexture( GLES11.GL_TEXTURE_2D,texId );
		
		if( MonkeyConfig.MOJO_IMAGE_FILTERING_ENABLED.equals( "1" ) ){
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MAG_FILTER,GLES11.GL_LINEAR );
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MIN_FILTER,GLES11.GL_LINEAR );
		}else{
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MAG_FILTER,GLES11.GL_NEAREST );
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MIN_FILTER,GLES11.GL_NEAREST );
		}

		GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_WRAP_S,GLES11.GL_CLAMP_TO_EDGE );
		GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_WRAP_T,GLES11.GL_CLAMP_TO_EDGE );
		
		int pwidth=(width==twidth) ? width : width+1;
		int pheight=(height==theight) ? height : height+1;

		Bitmap ibitmap=bitmap,bitmap2=null;
		
		if( width!=pwidth || height!=pheight ){
		
			bitmap2=Bitmap.createBitmap( twidth,theight,bitmap.getConfig() );
			Canvas canvas=new Canvas( bitmap2 );
			canvas.drawBitmap( bitmap,0,0,null );

			if( width!=pwidth ){
				canvas.save();
				canvas.clipRect( width,0,pwidth,height );
				canvas.drawBitmap( bitmap,1,0,null );
				canvas.restore();
				if( height!=pheight ){
					canvas.save();
					canvas.clipRect( 0,height,pwidth,pheight );
					canvas.drawBitmap( bitmap2,0,1,null );
					canvas.restore();
				}
			}else if( height!=pheight ){
				canvas.save();
				canvas.clipRect( 0,height,width,pheight );
				canvas.drawBitmap( bitmap,0,1,null );
				canvas.restore();
			}

			ibitmap=bitmap2;
		}
		
		int format=GLUtils.getInternalFormat( ibitmap ),type=GLUtils.getType( ibitmap );
		
		GLES11.glTexImage2D( GLES11.GL_TEXTURE_2D,0,format,twidth,theight,0,format,type,null );
		
		GLUtils.texSubImage2D( GLES11.GL_TEXTURE_2D,0,0,0,ibitmap );

		if( bitmap2!=null ) bitmap2.recycle();
	}

	void Bind(){
	
		if( seq==gxtkGraphics.seq ){
			GLES11.glBindTexture( GLES11.GL_TEXTURE_2D,texId );
			return;
		}
        
        if( bitmap==null ) throw new Error( "Attempt to use discarded image" );
		
		FlushDiscarded( true );

		int[] texs=new int[1];
		GLES11.glGenTextures( 1,texs,0 );
		texId=texs[0];
		if( texId==0 ) throw new Error( "glGenTextures failed" );
		seq=gxtkGraphics.seq;
		
		GLES11.glBindTexture( GLES11.GL_TEXTURE_2D,texId );
		
		if( MonkeyConfig.MOJO_IMAGE_FILTERING_ENABLED.equals( "1" ) ){
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MAG_FILTER,GLES11.GL_LINEAR );
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MIN_FILTER,GLES11.GL_LINEAR );
		}else{
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MAG_FILTER,GLES11.GL_NEAREST );
			GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_MIN_FILTER,GLES11.GL_NEAREST );
		}

		GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_WRAP_S,GLES11.GL_CLAMP_TO_EDGE );
		GLES11.glTexParameteri( GLES11.GL_TEXTURE_2D,GLES11.GL_TEXTURE_WRAP_T,GLES11.GL_CLAMP_TO_EDGE );
		
		int pwidth=(width==twidth) ? width : width+1;
		int pheight=(height==theight) ? height : height+1;

		int sz=pwidth*pheight;
		int[] pixels=new int[sz];
		bitmap.getPixels( pixels,0,pwidth,0,0,width,height );
		
		//pad edges for non pow-2 images - not sexy!
		if( width!=pwidth ){
			for( int y=0;y<height;++y ){
				pixels[y*pwidth+width]=pixels[y*pwidth+width-1];
			}
		}
		if( height!=pheight ){
			for( int x=0;x<width;++x ){
				pixels[height*pwidth+x]=pixels[height*pwidth+x-pwidth];
			}
		}
		if( width!=pwidth && height!=pheight ){
			pixels[height*pwidth+width]=pixels[height*pwidth+width-pwidth-1];
		}
		
		GLES11.glPixelStorei( GLES11.GL_UNPACK_ALIGNMENT,1 );
		
		boolean hicolor_textures=MonkeyConfig.MOJO_HICOLOR_TEXTURES.equals( "1" );
		
		if( hicolor_textures && hasAlpha ){

			//RGBA8888...
			ByteBuffer buf=ByteBuffer.allocate( sz*4 );
			buf.order( ByteOrder.BIG_ENDIAN );

			for( int i=0;i<sz;++i ){
				int p=pixels[i];
				int a=(p>>24) & 255;
				int r=((p>>16) & 255)*a/255;
				int g=((p>>8) & 255)*a/255;
				int b=(p & 255)*a/255;
				buf.putInt( (r<<24)|(g<<16)|(b<<8)|a );
			}
			buf.position( 0 );
			GLES11.glTexImage2D( GLES11.GL_TEXTURE_2D,0,GLES11.GL_RGBA,twidth,theight,0,GLES11.GL_RGBA,GLES11.GL_UNSIGNED_BYTE,null );
			GLES11.glTexSubImage2D( GLES11.GL_TEXTURE_2D,0,0,0,pwidth,pheight,GLES11.GL_RGBA,GLES11.GL_UNSIGNED_BYTE,buf );

		}else if( hicolor_textures && !hasAlpha ){
		
			//RGB888...
			ByteBuffer buf=ByteBuffer.allocate( sz*3 );
			buf.order( ByteOrder.BIG_ENDIAN );
			
			for( int i=0;i<sz;++i ){
				int p=pixels[i];
				int r=(p>>16) & 255;
				int g=(p>>8) & 255;
				int b=p & 255;
				buf.put( (byte)r );
				buf.put( (byte)g );
				buf.put( (byte)b );
			}
			buf.position( 0 );
			GLES11.glTexImage2D( GLES11.GL_TEXTURE_2D,0,GLES11.GL_RGB,twidth,theight,0,GLES11.GL_RGB,GLES11.GL_UNSIGNED_BYTE,null );
			GLES11.glTexSubImage2D( GLES11.GL_TEXTURE_2D,0,0,0,pwidth,pheight,GLES11.GL_RGB,GLES11.GL_UNSIGNED_BYTE,buf );
			
		}else if( !hicolor_textures && hasAlpha ){

			//16 bit RGBA...
			ByteBuffer buf=ByteBuffer.allocate( sz*2 );
			buf.order( ByteOrder.LITTLE_ENDIAN );
			
			//do we need 4 bit alpha?
			boolean a4=false;
			for( int i=0;i<sz;++i ){
				int a=(pixels[i]>>28) & 15;
				if( a!=0 && a!=15 ){
					a4=true;
					break;
				}
			}
			if( a4 ){
				//RGBA4444...
				for( int i=0;i<sz;++i ){
					int p=pixels[i];
					int a=(p>>28) & 15;
					int r=((p>>20) & 15)*a/15;
					int g=((p>>12) & 15)*a/15;
					int b=((p>> 4) & 15)*a/15;
					buf.putShort( (short)( (r<<12)|(g<<8)|(b<<4)|a ) );
				}
				buf.position( 0 );
				GLES11.glTexImage2D( GLES11.GL_TEXTURE_2D,0,GLES11.GL_RGBA,twidth,theight,0,GLES11.GL_RGBA,GLES11.GL_UNSIGNED_SHORT_4_4_4_4,null );
				GLES11.glTexSubImage2D( GLES11.GL_TEXTURE_2D,0,0,0,pwidth,pheight,GLES11.GL_RGBA,GLES11.GL_UNSIGNED_SHORT_4_4_4_4,buf );
			}else{
				//RGBA5551...
				for( int i=0;i<sz;++i ){
					int p=pixels[i];
					int a=(p>>31) & 1;
					int r=((p>>19) & 31)*a;
					int g=((p>>11) & 31)*a;
					int b=((p>> 3) & 31)*a;
					buf.putShort( (short)( (r<<11)|(g<<6)|(b<<1)|a ) );
				}
				buf.position( 0 );
				GLES11.glTexImage2D( GLES11.GL_TEXTURE_2D,0,GLES11.GL_RGBA,twidth,theight,0,GLES11.GL_RGBA,GLES11.GL_UNSIGNED_SHORT_5_5_5_1,null );
				GLES11.glTexSubImage2D( GLES11.GL_TEXTURE_2D,0,0,0,pwidth,pheight,GLES11.GL_RGBA,GLES11.GL_UNSIGNED_SHORT_5_5_5_1,buf );
			}
		}else if( !hicolor_textures && !hasAlpha ){
		
			ByteBuffer buf=ByteBuffer.allocate( sz*2 );
			buf.order( ByteOrder.LITTLE_ENDIAN );
			
			//RGB565...
			for( int i=0;i<sz;++i ){
				int p=pixels[i];
				int r=(p>>19) & 31;
				int g=(p>>10) & 63;
				int b=(p>> 3) & 31;
				buf.putShort( (short)( (r<<11)|(g<<5)|b ) );
			}
			buf.position( 0 );
			GLES11.glTexImage2D( GLES11.GL_TEXTURE_2D,0,GLES11.GL_RGB,twidth,theight,0,GLES11.GL_RGB,GLES11.GL_UNSIGNED_SHORT_5_6_5,null );
			GLES11.glTexSubImage2D( GLES11.GL_TEXTURE_2D,0,0,0,pwidth,pheight,GLES11.GL_RGB,GLES11.GL_UNSIGNED_SHORT_5_6_5,buf );
		}
	}

	//***** GXTK API *****
	
	int Discard(){
		Invalidate();
		bitmap=null;
		return 0;
	}

	int Width(){
		return width;
	}
	
	int Height(){
		return height;
	}

	int Loaded(){
		return 1;
	}
	
	void OnUnsafeLoadComplete(){
	}
}

class gxtkAudio{

	static class gxtkChannel{
		int stream;		//SoundPool stream ID, 0=none
		float volume=1;
		float rate=1;
		float pan;
		int state;
	};
	
	BBAndroidGame game;
	SoundPool pool;
	MediaPlayer music;
	float musicVolume=1;
	int musicState=0;
	
	gxtkChannel[] channels=new gxtkChannel[32];
	
	gxtkAudio(){
		game=BBAndroidGame.AndroidGame();
		pool=new SoundPool( 32,AudioManager.STREAM_MUSIC,0 );
		for( int i=0;i<32;++i ){
			channels[i]=new gxtkChannel();
		}
	}
	
	void OnDestroy(){
		for( int i=0;i<32;++i ){
			if( channels[i].state!=0 ) pool.stop( channels[i].stream );
		}
		pool.release();
		pool=null;
	}
	
	//***** GXTK API *****
	int Suspend(){
		if( musicState==1 ) music.pause();
		for( int i=0;i<32;++i ){
			if( channels[i].state==1 ) pool.pause( channels[i].stream );
		}
		return 0;
	}
	
	int Resume(){
		if( musicState==1 ) music.start();
		for( int i=0;i<32;++i ){
			if( channels[i].state==1 ) pool.resume( channels[i].stream );
		}
		return 0;
	}
	

	boolean LoadSample__UNSAFE__( gxtkSample sample,String path ){
		gxtkSample.FlushDiscarded( pool );
		int sound=game.LoadSound( path,pool );
		if( sound==0 ) return false;
		sample.SetSound( sound );
		return true;
	}
	
	gxtkSample LoadSample( String path ){
		gxtkSample sample=new gxtkSample();
		if( !LoadSample__UNSAFE__( sample,path ) ) return null;
		return sample;
	}
	
	int PlaySample( gxtkSample sample,int channel,int flags ){
		gxtkChannel chan=channels[channel];
		if( chan.state!=0 ) pool.stop( chan.stream );
		float rv=(chan.pan * .5f + .5f) * chan.volume;
		float lv=chan.volume-rv;
		int loops=(flags&1)!=0 ? -1 : 0;

		//chan.stream=pool.play( sample.sound,lv,rv,0,loops,chan.rate );
		//chan.state=1;
		//return 0;
		
		//Ugly as hell, but seems to work for now...pauses 10 secs max...
		for( int i=0;i<100;++i ){
			chan.stream=pool.play( sample.sound,lv,rv,0,loops,chan.rate );
			if( chan.stream!=0 ){
				chan.state=1;
				return 0;
			}
//			throw new Error( "PlaySample failed to play sound" );
			try{
				Thread.sleep( 100 );
			}catch( java.lang.InterruptedException ex ){
			}
		}
		throw new Error( "PlaySample failed to play sound" );
	}
	
	int StopChannel( int channel ){
		gxtkChannel chan=channels[channel];
		if( chan.state!=0 ){
			pool.stop( chan.stream );
			chan.state=0;
		}
		return 0;
	}
	
	int PauseChannel( int channel ){
		gxtkChannel chan=channels[channel];
		if( chan.state==1 ){
			pool.pause( chan.stream );
			chan.state=2;
		}
		return 0;
	}
	
	int ResumeChannel( int channel ){
		gxtkChannel chan=channels[channel];
		if( chan.state==2 ){
			pool.resume( chan.stream );
			chan.state=1;
		}
		return 0;
	}
	
	int ChannelState( int channel ){
		return -1;
	}
	
	int SetVolume( int channel,float volume ){
		gxtkChannel chan=channels[channel];
		chan.volume=volume;
		if( chan.stream!=0 ){
			float rv=(chan.pan * .5f + .5f) * chan.volume;
			float lv=chan.volume-rv;
			pool.setVolume( chan.stream,lv,rv );
		}
		return 0;
	}
	
	int SetPan( int channel,float pan ){
		gxtkChannel chan=channels[channel];
		chan.pan=pan;
		if( chan.stream!=0 ){
			float rv=(chan.pan * .5f + .5f) * chan.volume;
			float lv=chan.volume-rv;
			pool.setVolume( chan.stream,lv,rv );
		}
		return 0;
	}

	int SetRate( int channel,float rate ){
		gxtkChannel chan=channels[channel];
		chan.rate=rate;
		if( chan.stream!=0 ){
			pool.setRate( chan.stream,chan.rate );
		}
		return 0;
	}
	
	int PlayMusic( String path,int flags ){
		StopMusic();
		music=game.OpenMedia( path );
		if( music==null ) return -1;
		music.setLooping( (flags&1)!=0 );
		music.setVolume( musicVolume,musicVolume );
		music.start();
		musicState=1;
		return 0;
	}
	
	int StopMusic(){
		if( musicState!=0 ){
			music.stop();
			music.release();
			musicState=0;
			music=null;
		}
		return 0;
	}
	
	int PauseMusic(){
		if( musicState==1 && music.isPlaying() ){
			music.pause();
			musicState=2;
		}
		return 0;
	}
	
	int ResumeMusic(){
		if( musicState==2 ){
			music.start();
			musicState=1;
		}
		return 0;
	}
	
	int MusicState(){
		if( musicState==1 && !music.isPlaying() ) musicState=0;
		return musicState;
	}
	
	int SetMusicVolume( float volume ){
		if( musicState!=0 ) music.setVolume( volume,volume );
		musicVolume=volume;
		return 0;
	}	
}

class gxtkSample{

	int sound;
	
	static Vector discarded=new Vector();
	
	gxtkSample(){
	}
	
	gxtkSample( int sound ){
		this.sound=sound;
	}
	
	void SetSound( int sound ){
		this.sound=sound;
	}
	
	protected void finalize(){
		Discard();
	}
	
	static void FlushDiscarded( SoundPool pool ){
		int n=discarded.size();
		if( n==0 ) return;
		Vector out=new Vector();
		for( int i=0;i<n;++i ){
			Integer val=(Integer)discarded.elementAt(i);
			if( pool.unload( val.intValue() ) ){
//				bb_std_lang.print( "unload OK!" );
			}else{
//				bb_std_lang.print( "unload failed!" );
				out.add( val );
			}
		}
		discarded=out;
//		bb_std_lang.print( "undiscarded="+out.size() );
	}

	//***** GXTK API *****
	
	int Discard(){
		if( sound!=0 ){
			discarded.add( Integer.valueOf( sound ) );
			sound=0;
		}
		return 0;
	}
}


class BBThread implements Runnable{

	Object _result;
	boolean _running;
	Thread _thread;
	
	boolean IsRunning(){
		return _running;
	}
	
	void Start(){
		if( _running ) return;
		_result=null;
		_running=true;
		_thread=new Thread( this );
		_thread.start();
	}
	
	void SetResult( Object result ){
		_result=result;
	}
	
	Object Result(){
		return _result;
	}
	
	void Wait(){
		while( _running ){
			try{
				_thread.join();
			}catch( InterruptedException ex ){
			}
		}
	}
	
	void Run__UNSAFE__(){
	}

	public void run(){
		Run__UNSAFE__();
		_running=false;
	}
}


class BBHttpRequest extends BBThread{

	HttpURLConnection _con;
	String _response;
	int _status;
	int _recv;
	
	String _sendText,_encoding;
	
	void Open( String req,String url ){
		try{
			URL turl=new URL( url );
			_con=(HttpURLConnection)turl.openConnection();
			_con.setRequestMethod( req );
		}catch( Exception ex ){
		}		
		_response="";
		_status=-1;
		_recv=0;
	}
	
	void SetHeader( String name,String value ){
		_con.setRequestProperty( name,value );
	}
	
	void Send(){
		_sendText=_encoding=null;
		Start();
	}
	
	void SendText( String text,String encoding ){
		_sendText=text;
		_encoding=encoding;
		Start();
	}
	
	String ResponseText(){
		return _response;
	}
	
	int Status(){
		return _status;
	}
	
	void Run__UNSAFE__(){
		try{
			if( _sendText!=null ){
			
				byte[] bytes=_sendText.getBytes( "UTF-8" );

				_con.setDoOutput( true );
				_con.setFixedLengthStreamingMode( bytes.length );
				
				OutputStream out=_con.getOutputStream();
				out.write( bytes,0,bytes.length );
				out.close();
			}
			
			InputStream in=_con.getInputStream();

			byte[] buf=new byte[4096];
			ByteArrayOutputStream out=new ByteArrayOutputStream( 1024 );
			for(;;){
				int n=in.read( buf );
				if( n<0 ) break;
				out.write( buf,0,n );
				_recv+=n;
			}
			in.close();
			
			_response=new String( out.toByteArray(),"UTF-8" );

			_status=_con.getResponseCode();
			
		}catch( IOException ex ){
		}
		
		_con.disconnect();
	}
	
	int BytesReceived(){
		return _recv;
	}
}

class c_App extends Object{
	public final c_App m_App_new(){
		if((bb_app.g__app)!=null){
			bb_std_lang.error("App has already been created");
		}
		bb_app.g__app=this;
		bb_app.g__delegate=(new c_GameDelegate()).m_GameDelegate_new();
		bb_app.g__game.SetDelegate(bb_app.g__delegate);
		return this;
	}
	public final int p_OnResize(){
		return 0;
	}
	public int p_OnCreate(){
		return 0;
	}
	public final int p_OnSuspend(){
		return 0;
	}
	public final int p_OnResume(){
		return 0;
	}
	public int p_OnUpdate(){
		return 0;
	}
	public final int p_OnLoading(){
		return 0;
	}
	public int p_OnRender(){
		return 0;
	}
	public final int p_OnClose(){
		bb_app.g_EndApp();
		return 0;
	}
	public final int p_OnBack(){
		p_OnClose();
		return 0;
	}
}
interface c_IOnHttpRequestComplete{
	public void p_OnHttpRequestComplete(c_HttpRequest t_req);
}
class c_PrismShipGame extends c_App implements c_IOnHttpRequestComplete{
	public final c_PrismShipGame m_PrismShipGame_new(){
		super.m_App_new();
		return this;
	}
	float m_lastEnemyTime=0.0f;
	c_Image m_redEnemyImg=null;
	c_Image m_blueEnemyImg=null;
	c_Image m_greenEnemyImg=null;
	c_Image[] m_enemyImages=new c_Image[0];
	c_Image m_redPlayerImg=null;
	c_Image m_bluePlayerImg=null;
	c_Image m_greenPlayerImg=null;
	c_Image[] m_playerImages=new c_Image[0];
	c_Image m_redBulletImg=null;
	c_Image m_blueBulletImg=null;
	c_Image m_greenBulletImg=null;
	c_Image[] m_bulletImages=new c_Image[0];
	c_Player m_player=null;
	boolean m_keyboard_enabled=false;
	public final int p_OnCreate(){
		bb_std_lang.print("Creating Game");
		bb_app.g_SetUpdateRate(60);
		m_lastEnemyTime=(float)(bb_app.g_Millisecs());
		bb_random.g_Seed=bb_app.g_Millisecs();
		m_redEnemyImg=bb_graphics.g_LoadImage("ENEMY_RED.png",1,c_Image.m_DefaultFlags);
		m_blueEnemyImg=bb_graphics.g_LoadImage("ENEMY_BLUE.png",1,c_Image.m_DefaultFlags);
		m_greenEnemyImg=bb_graphics.g_LoadImage("ENEMY_GREEN.png",1,c_Image.m_DefaultFlags);
		m_enemyImages=new c_Image[]{m_redEnemyImg,m_greenEnemyImg,m_blueEnemyImg};
		m_redPlayerImg=bb_graphics.g_LoadImage("PLAYER_RED.png",1,c_Image.m_DefaultFlags);
		m_bluePlayerImg=bb_graphics.g_LoadImage("PLAYER_BLUE.png",1,c_Image.m_DefaultFlags);
		m_greenPlayerImg=bb_graphics.g_LoadImage("PLAYER_GREEN.png",1,c_Image.m_DefaultFlags);
		m_playerImages=new c_Image[]{m_redPlayerImg,m_greenPlayerImg,m_bluePlayerImg};
		m_redBulletImg=bb_graphics.g_LoadImage("BULLET_RED.png",1,c_Image.m_DefaultFlags);
		m_blueBulletImg=bb_graphics.g_LoadImage("BULLET_BLUE.png",1,c_Image.m_DefaultFlags);
		m_greenBulletImg=bb_graphics.g_LoadImage("BULLET_GREEN.png",1,c_Image.m_DefaultFlags);
		m_bulletImages=new c_Image[]{m_redBulletImg,m_greenBulletImg,m_blueBulletImg};
		m_player=(new c_Player()).m_Player_new(220.0f,384.0f,0.0f,0,400.0f,m_playerImages,3.0f);
		m_keyboard_enabled=false;
		return 0;
	}
	int m_gameState=0;
	String m_controls="";
	String m_initials="";
	public final int p_UpdatePlayer(c_Player t_player){
		t_player.p_Update();
		if(t_player.m_position.m_x<0.0f){
			t_player.p_SetPosition(0.0f,t_player.m_position.m_y);
		}
		if(t_player.m_position.m_x>616.0f){
			t_player.p_SetPosition(616.0f,t_player.m_position.m_y);
		}
		if(t_player.m_position.m_y>432.0f){
			t_player.p_SetPosition(t_player.m_position.m_x,432.0f);
		}
		if(t_player.m_position.m_y<0.0f){
			t_player.p_SetPosition(t_player.m_position.m_x,0.0f);
		}
		return 0;
	}
	c_List m_bullets=(new c_List()).m_List_new();
	public final int p_UpdateBullets(c_List t_bullets){
		c_Enumerator t_=t_bullets.p_ObjectEnumerator();
		while(t_.p_HasNext()){
			c_Bullet t_bullet=t_.p_NextObject();
			t_bullet.p_Update();
		}
		return 0;
	}
	float m_nextEnemyDiff=1000.0f;
	int m_score=0;
	c_List2 m_enemies=(new c_List2()).m_List_new();
	public final int p_GenerateEnemy(){
		if((float)(bb_app.g_Millisecs())-m_lastEnemyTime>=bb_math.g_Max2(m_nextEnemyDiff-(float)(m_score),200.0f)){
			m_enemies.p_AddLast2((new c_Enemy()).m_Enemy_new(bb_random.g_Rnd2(2.0f,5.0f),bb_random.g_Rnd3(3.0f),20.0f+bb_random.g_Rnd()*600.0f,-32.0f,m_enemyImages));
			m_lastEnemyTime=(float)(bb_app.g_Millisecs());
			m_nextEnemyDiff=1000.0f*bb_random.g_Rnd2(0.5f,1.0f);
		}
		return 0;
	}
	public final int p_UpdateEnemies(c_List2 t_enemies){
		c_Enumerator2 t_=t_enemies.p_ObjectEnumerator();
		while(t_.p_HasNext()){
			c_Enemy t_enemy=t_.p_NextObject();
			t_enemy.p_Update();
		}
		return 0;
	}
	public final int p_Collided(c_CollisionRect t_box1,c_CollisionRect t_box2){
		if(t_box1.m_position.m_x<t_box2.m_position.m_x+(float)(t_box2.m_width) && t_box1.m_position.m_x+(float)(t_box1.m_width)>t_box2.m_position.m_x && t_box1.m_position.m_y<t_box2.m_position.m_y+(float)(t_box2.m_height) && (float)(t_box1.m_height)+t_box1.m_position.m_y>t_box2.m_position.m_y){
			return 1;
		}else{
			return 0;
		}
	}
	public final int p_SameColor(c_Bullet t_bullet,c_Enemy t_enemy){
		if(t_bullet.m_colors[0]==t_enemy.m_colors[0] && t_bullet.m_colors[1]==t_enemy.m_colors[1] && t_bullet.m_colors[2]==t_enemy.m_colors[2]){
			return 1;
		}else{
			return 0;
		}
	}
	public final int p_DestroyThings(c_List t_bullets,c_List2 t_enemies){
		c_Enumerator t_=t_bullets.p_ObjectEnumerator();
		while(t_.p_HasNext()){
			c_Bullet t_bullet=t_.p_NextObject();
			c_Enumerator2 t_2=t_enemies.p_ObjectEnumerator();
			while(t_2.p_HasNext()){
				c_Enemy t_enemy=t_2.p_NextObject();
				if(((p_Collided(t_bullet.m_box,t_enemy.m_box))!=0) && ((p_SameColor(t_bullet,t_enemy))!=0)){
					t_bullets.p_Remove(t_bullet);
					t_enemies.p_Remove3(t_enemy);
					m_score+=10;
				}else{
					if(((p_Collided(t_bullet.m_box,t_enemy.m_box))!=0) && !((p_SameColor(t_bullet,t_enemy))!=0)){
						t_bullets.p_Remove(t_bullet);
					}
				}
			}
		}
		return 0;
	}
	String m_highScoreServer="https://prismship.herokuapp.com/";
	c_HttpRequest m_postReq=null;
	public final int p_PostScore(){
		m_postReq=(new c_HttpRequest()).m_HttpRequest_new2("POST",m_highScoreServer+m_initials+"/"+String.valueOf(m_score),(this));
		bb_std_lang.print(m_highScoreServer+m_initials+"/"+String.valueOf(m_score));
		m_postReq.p_Send();
		return 0;
	}
	public final int p_CheckDeath(c_Player t_player,c_List2 t_enemies){
		c_Enumerator2 t_=t_enemies.p_ObjectEnumerator();
		while(t_.p_HasNext()){
			c_Enemy t_enemy=t_.p_NextObject();
			if((p_Collided(t_player.m_box,t_enemy.m_box))!=0){
				m_gameState=2;
				p_PostScore();
			}
		}
		return 0;
	}
	float m_lastColorChange=0.0f;
	float m_colorChangeTime=7000.0f;
	public final int p_ChangeColor(c_Player t_player){
		if((float)(bb_app.g_Millisecs())-m_lastColorChange>=m_colorChangeTime){
			t_player.p_ChangeColor2();
			m_lastColorChange=(float)(bb_app.g_Millisecs());
		}
		return 0;
	}
	public final int p_Reset(){
		m_player.p_Reset();
		m_enemies.p_Clear();
		m_bullets.p_Clear();
		m_score=0;
		m_gameState=0;
		return 0;
	}
	public final int p_OnUpdate(){
		int t_1=m_gameState;
		if(t_1==0){
			if((bb_input.g_KeyHit(13))!=0){
				m_player.p_SetSpeed(3.0f);
				m_controls="KEYBOARD";
				m_gameState=3;
			}else{
				if(((bb_input.g_TouchDown(0))!=0) && bb_input.g_TouchX(0)>200.0f && bb_input.g_TouchX(0)<400.0f && bb_input.g_TouchY(0)>300.0f && bb_input.g_TouchY(0)<320.0f){
					m_player.p_SetSpeed(4.0f);
					m_controls="TOUCH";
					m_gameState=3;
				}
			}
		}else{
			if(t_1==3){
				if(!m_keyboard_enabled){
					bb_input.g_EnableKeyboard();
					m_keyboard_enabled=true;
				}
				int t_char=bb_input.g_GetChar();
				if(t_char==13){
					bb_input.g_DisableKeyboard();
					m_keyboard_enabled=false;
					m_gameState=1;
				}else{
					if(t_char==8 || t_char==127){
						if(m_initials.length()<=1){
							m_initials="";
						}else{
							m_initials=bb_std_lang.slice(m_initials,0,-1);
						}
					}else{
						if(t_char>0 && m_initials.length()<3){
							m_initials=m_initials+String.valueOf((char)(t_char));
						}
					}
				}
			}else{
				if(t_1==1){
					p_UpdatePlayer(m_player);
					p_UpdateBullets(m_bullets);
					if(((bb_input.g_KeyHit(32))!=0) || ((bb_input.g_TouchDown(0))!=0) && ((bb_input.g_TouchDown(1))!=0)){
						if((float)(bb_app.g_Millisecs())-m_player.m_lastBullet>=m_player.m_fireRate){
							m_bullets.p_AddLast(m_player.p_Fire(m_bulletImages));
							m_player.m_lastBullet=(float)(bb_app.g_Millisecs());
						}
					}
					p_GenerateEnemy();
					p_UpdateEnemies(m_enemies);
					p_DestroyThings(m_bullets,m_enemies);
					p_CheckDeath(m_player,m_enemies);
					p_ChangeColor(m_player);
				}else{
					if(t_1==2){
						if(((bb_input.g_KeyHit(13))!=0) || ((bb_input.g_TouchDown(0))!=0)){
							p_Reset();
						}
					}
				}
			}
		}
		bb_asyncevent.g_UpdateAsyncEvents();
		return 0;
	}
	String m_highScores="";
	public final int p_DrawScores(float t_beginHeight){
		String[] t_scoreList=bb_std_lang.split(m_highScores,"\n");
		float t_ypos=t_beginHeight;
		for(int t_i=0;t_i<bb_std_lang.length(t_scoreList);t_i=t_i+1){
			bb_graphics.g_DrawText(t_scoreList[t_i],320.0f,t_ypos,0.0f,0.0f);
			t_ypos=t_ypos+10.0f;
		}
		return 0;
	}
	public final int p_OnRender(){
		bb_graphics.g_Cls(100.0f,100.0f,100.0f);
		int t_2=m_gameState;
		if(t_2==0){
			bb_graphics.g_DrawText("PrismShip",320.0f,200.0f,0.5f,0.0f);
			bb_graphics.g_DrawText("Hit ENTER to use keyboard controls",320.0f,250.0f,0.5f,0.0f);
			bb_graphics.g_DrawText("Or Touch Screen HERE To use touch controls",320.0f,300.0f,0.5f,0.0f);
		}else{
			if(t_2==3){
				if(m_controls.compareTo("KEYBOARD")==0){
					bb_graphics.g_DrawText("Keyboard Controls",320.0f,100.0f,0.5f,0.0f);
					bb_graphics.g_DrawText("-- Use W, A, S, D to Move",320.0f,130.0f,0.5f,0.0f);
					bb_graphics.g_DrawText("-- Use SPACE key to Fire",320.0f,160.0f,0.5f,0.0f);
					bb_graphics.g_DrawText("Enter your initials: "+m_initials,320.0f,190.0f,0.5f,0.0f);
					m_player.m_controls=0;
				}else{
					if(m_controls.compareTo("TOUCH")==0){
						bb_graphics.g_DrawText("Touch Controls",320.0f,100.0f,0.5f,0.0f);
						bb_graphics.g_DrawText("-- Touch to one side of the ship to Move in that direction",320.0f,130.0f,0.5f,0.0f);
						bb_graphics.g_DrawText("-- Touch with 2 fingers to Fire",320.0f,160.0f,0.5f,0.0f);
						bb_graphics.g_DrawText("Enter your initials to play: "+m_initials,320.0f,190.0f,0.5f,0.0f);
						m_player.m_controls=1;
					}
				}
				bb_graphics.g_DrawText("Shoot Blocks the same color as you to score",320.0f,250.0f,0.5f,0.0f);
				bb_graphics.g_DrawText("Get hit by a block and you lose",320.0f,280.0f,0.5f,0.0f);
			}else{
				if(t_2==1){
					bb_graphics.g_PushMatrix();
					c_Enumerator t_=m_bullets.p_ObjectEnumerator();
					while(t_.p_HasNext()){
						c_Bullet t_bullet=t_.p_NextObject();
						if(t_bullet.m_position.m_y<0.0f){
							m_bullets.p_Remove(t_bullet);
						}else{
							t_bullet.p_Draw();
						}
					}
					c_Enumerator2 t_3=m_enemies.p_ObjectEnumerator();
					while(t_3.p_HasNext()){
						c_Enemy t_enemy=t_3.p_NextObject();
						if(t_enemy.m_position.m_y>480.0f){
							m_enemies.p_Remove3(t_enemy);
						}else{
							t_enemy.p_Draw();
						}
					}
					m_player.p_Draw();
					bb_graphics.g_SetColor(255.0f,255.0f,255.0f);
					bb_graphics.g_DrawText("Score: "+String.valueOf(m_score),20.0f,20.0f,0.0f,0.0f);
					bb_graphics.g_PopMatrix();
				}else{
					if(t_2==2){
						bb_graphics.g_DrawText("GAME OVER",320.0f,115.0f,0.5f,0.0f);
						bb_graphics.g_DrawText("INITIALS: "+m_initials,320.0f,130.0f,0.5f,0.0f);
						bb_graphics.g_DrawText("SCORE: "+String.valueOf(m_score),320.0f,160.0f,0.5f,0.0f);
						bb_graphics.g_DrawText("Hit ENTER or Touch the screen to try again!",320.0f,190.0f,0.5f,0.0f);
						p_DrawScores(220.0f);
					}
				}
			}
		}
		return 0;
	}
	c_HttpRequest m_getReq=null;
	public final int p_GetScores(){
		m_getReq=(new c_HttpRequest()).m_HttpRequest_new2("GET",m_highScoreServer,(this));
		bb_std_lang.print("Sending Get Request");
		m_getReq.p_Send();
		bb_std_lang.print("Get Request Sent");
		return 0;
	}
	public final void p_OnHttpRequestComplete(c_HttpRequest t_req){
		if(t_req==m_getReq){
			bb_std_lang.print("Get Complete");
		}else{
			bb_std_lang.print("Post Complete");
			p_GetScores();
		}
		bb_std_lang.print(String.valueOf(t_req.p_Status()));
		m_highScores=t_req.p_ResponseText();
	}
}
class c_GameDelegate extends BBGameDelegate{
	public final c_GameDelegate m_GameDelegate_new(){
		return this;
	}
	gxtkGraphics m__graphics=null;
	gxtkAudio m__audio=null;
	c_InputDevice m__input=null;
	public final void StartGame(){
		m__graphics=(new gxtkGraphics());
		bb_graphics.g_SetGraphicsDevice(m__graphics);
		bb_graphics.g_SetFont(null,32);
		m__audio=(new gxtkAudio());
		bb_audio.g_SetAudioDevice(m__audio);
		m__input=(new c_InputDevice()).m_InputDevice_new();
		bb_input.g_SetInputDevice(m__input);
		bb_app.g_ValidateDeviceWindow(false);
		bb_app.g_EnumDisplayModes();
		bb_app.g__app.p_OnCreate();
	}
	public final void SuspendGame(){
		bb_app.g__app.p_OnSuspend();
		m__audio.Suspend();
	}
	public final void ResumeGame(){
		m__audio.Resume();
		bb_app.g__app.p_OnResume();
	}
	public final void UpdateGame(){
		bb_app.g_ValidateDeviceWindow(true);
		m__input.p_BeginUpdate();
		bb_app.g__app.p_OnUpdate();
		m__input.p_EndUpdate();
	}
	public final void RenderGame(){
		bb_app.g_ValidateDeviceWindow(true);
		int t_mode=m__graphics.BeginRender();
		if((t_mode)!=0){
			bb_graphics.g_BeginRender();
		}
		if(t_mode==2){
			bb_app.g__app.p_OnLoading();
		}else{
			bb_app.g__app.p_OnRender();
		}
		if((t_mode)!=0){
			bb_graphics.g_EndRender();
		}
		m__graphics.EndRender();
	}
	public final void KeyEvent(int t_event,int t_data){
		m__input.p_KeyEvent(t_event,t_data);
		if(t_event!=1){
			return;
		}
		int t_1=t_data;
		if(t_1==432){
			bb_app.g__app.p_OnClose();
		}else{
			if(t_1==416){
				bb_app.g__app.p_OnBack();
			}
		}
	}
	public final void MouseEvent(int t_event,int t_data,float t_x,float t_y){
		m__input.p_MouseEvent(t_event,t_data,t_x,t_y);
	}
	public final void TouchEvent(int t_event,int t_data,float t_x,float t_y){
		m__input.p_TouchEvent(t_event,t_data,t_x,t_y);
	}
	public final void MotionEvent(int t_event,int t_data,float t_x,float t_y,float t_z){
		m__input.p_MotionEvent(t_event,t_data,t_x,t_y,t_z);
	}
	public final void DiscardGraphics(){
		m__graphics.DiscardGraphics();
	}
}
class c_Image extends Object{
	static int m_DefaultFlags;
	public final c_Image m_Image_new(){
		return this;
	}
	gxtkSurface m_surface=null;
	int m_width=0;
	int m_height=0;
	c_Frame[] m_frames=new c_Frame[0];
	int m_flags=0;
	float m_tx=.0f;
	float m_ty=.0f;
	public final int p_SetHandle(float t_tx,float t_ty){
		this.m_tx=t_tx;
		this.m_ty=t_ty;
		this.m_flags=this.m_flags&-2;
		return 0;
	}
	public final int p_ApplyFlags(int t_iflags){
		m_flags=t_iflags;
		if((m_flags&2)!=0){
			c_Frame[] t_=m_frames;
			int t_2=0;
			while(t_2<bb_std_lang.length(t_)){
				c_Frame t_f=t_[t_2];
				t_2=t_2+1;
				t_f.m_x+=1;
			}
			m_width-=2;
		}
		if((m_flags&4)!=0){
			c_Frame[] t_3=m_frames;
			int t_4=0;
			while(t_4<bb_std_lang.length(t_3)){
				c_Frame t_f2=t_3[t_4];
				t_4=t_4+1;
				t_f2.m_y+=1;
			}
			m_height-=2;
		}
		if((m_flags&1)!=0){
			p_SetHandle((float)(m_width)/2.0f,(float)(m_height)/2.0f);
		}
		if(bb_std_lang.length(m_frames)==1 && m_frames[0].m_x==0 && m_frames[0].m_y==0 && m_width==m_surface.Width() && m_height==m_surface.Height()){
			m_flags|=65536;
		}
		return 0;
	}
	public final c_Image p_Init(gxtkSurface t_surf,int t_nframes,int t_iflags){
		if((m_surface)!=null){
			bb_std_lang.error("Image already initialized");
		}
		m_surface=t_surf;
		m_width=m_surface.Width()/t_nframes;
		m_height=m_surface.Height();
		m_frames=new c_Frame[t_nframes];
		for(int t_i=0;t_i<t_nframes;t_i=t_i+1){
			m_frames[t_i]=(new c_Frame()).m_Frame_new(t_i*m_width,0);
		}
		p_ApplyFlags(t_iflags);
		return this;
	}
	c_Image m_source=null;
	public final c_Image p_Init2(gxtkSurface t_surf,int t_x,int t_y,int t_iwidth,int t_iheight,int t_nframes,int t_iflags,c_Image t_src,int t_srcx,int t_srcy,int t_srcw,int t_srch){
		if((m_surface)!=null){
			bb_std_lang.error("Image already initialized");
		}
		m_surface=t_surf;
		m_source=t_src;
		m_width=t_iwidth;
		m_height=t_iheight;
		m_frames=new c_Frame[t_nframes];
		int t_ix=t_x;
		int t_iy=t_y;
		for(int t_i=0;t_i<t_nframes;t_i=t_i+1){
			if(t_ix+m_width>t_srcw){
				t_ix=0;
				t_iy+=m_height;
			}
			if(t_ix+m_width>t_srcw || t_iy+m_height>t_srch){
				bb_std_lang.error("Image frame outside surface");
			}
			m_frames[t_i]=(new c_Frame()).m_Frame_new(t_ix+t_srcx,t_iy+t_srcy);
			t_ix+=m_width;
		}
		p_ApplyFlags(t_iflags);
		return this;
	}
	public final int p_Width(){
		return m_width;
	}
	public final int p_Height(){
		return m_height;
	}
	public final int p_Frames(){
		return bb_std_lang.length(m_frames);
	}
}
class c_GraphicsContext extends Object{
	public final c_GraphicsContext m_GraphicsContext_new(){
		return this;
	}
	c_Image m_defaultFont=null;
	c_Image m_font=null;
	int m_firstChar=0;
	int m_matrixSp=0;
	float m_ix=1.0f;
	float m_iy=.0f;
	float m_jx=.0f;
	float m_jy=1.0f;
	float m_tx=.0f;
	float m_ty=.0f;
	int m_tformed=0;
	int m_matDirty=0;
	float m_color_r=.0f;
	float m_color_g=.0f;
	float m_color_b=.0f;
	float m_alpha=.0f;
	int m_blend=0;
	float m_scissor_x=.0f;
	float m_scissor_y=.0f;
	float m_scissor_width=.0f;
	float m_scissor_height=.0f;
	public final int p_Validate(){
		if((m_matDirty)!=0){
			bb_graphics.g_renderDevice.SetMatrix(bb_graphics.g_context.m_ix,bb_graphics.g_context.m_iy,bb_graphics.g_context.m_jx,bb_graphics.g_context.m_jy,bb_graphics.g_context.m_tx,bb_graphics.g_context.m_ty);
			m_matDirty=0;
		}
		return 0;
	}
	float[] m_matrixStack=new float[192];
}
class c_Frame extends Object{
	int m_x=0;
	int m_y=0;
	public final c_Frame m_Frame_new(int t_x,int t_y){
		this.m_x=t_x;
		this.m_y=t_y;
		return this;
	}
	public final c_Frame m_Frame_new2(){
		return this;
	}
}
class c_InputDevice extends Object{
	c_JoyState[] m__joyStates=new c_JoyState[4];
	public final c_InputDevice m_InputDevice_new(){
		for(int t_i=0;t_i<4;t_i=t_i+1){
			m__joyStates[t_i]=(new c_JoyState()).m_JoyState_new();
		}
		return this;
	}
	boolean[] m__keyDown=new boolean[512];
	int m__keyHitPut=0;
	int[] m__keyHitQueue=new int[33];
	int[] m__keyHit=new int[512];
	public final void p_PutKeyHit(int t_key){
		if(m__keyHitPut==bb_std_lang.length(m__keyHitQueue)){
			return;
		}
		m__keyHit[t_key]+=1;
		m__keyHitQueue[m__keyHitPut]=t_key;
		m__keyHitPut+=1;
	}
	public final void p_BeginUpdate(){
		for(int t_i=0;t_i<4;t_i=t_i+1){
			c_JoyState t_state=m__joyStates[t_i];
			if(!BBGame.Game().PollJoystick(t_i,t_state.m_joyx,t_state.m_joyy,t_state.m_joyz,t_state.m_buttons)){
				break;
			}
			for(int t_j=0;t_j<32;t_j=t_j+1){
				int t_key=256+t_i*32+t_j;
				if(t_state.m_buttons[t_j]){
					if(!m__keyDown[t_key]){
						m__keyDown[t_key]=true;
						p_PutKeyHit(t_key);
					}
				}else{
					m__keyDown[t_key]=false;
				}
			}
		}
	}
	int m__charGet=0;
	int m__charPut=0;
	public final void p_EndUpdate(){
		for(int t_i=0;t_i<m__keyHitPut;t_i=t_i+1){
			m__keyHit[m__keyHitQueue[t_i]]=0;
		}
		m__keyHitPut=0;
		m__charGet=0;
		m__charPut=0;
	}
	int[] m__charQueue=new int[32];
	public final void p_KeyEvent(int t_event,int t_data){
		int t_1=t_event;
		if(t_1==1){
			if(!m__keyDown[t_data]){
				m__keyDown[t_data]=true;
				p_PutKeyHit(t_data);
				if(t_data==1){
					m__keyDown[384]=true;
					p_PutKeyHit(384);
				}else{
					if(t_data==384){
						m__keyDown[1]=true;
						p_PutKeyHit(1);
					}
				}
			}
		}else{
			if(t_1==2){
				if(m__keyDown[t_data]){
					m__keyDown[t_data]=false;
					if(t_data==1){
						m__keyDown[384]=false;
					}else{
						if(t_data==384){
							m__keyDown[1]=false;
						}
					}
				}
			}else{
				if(t_1==3){
					if(m__charPut<bb_std_lang.length(m__charQueue)){
						m__charQueue[m__charPut]=t_data;
						m__charPut+=1;
					}
				}
			}
		}
	}
	float m__mouseX=.0f;
	float m__mouseY=.0f;
	float[] m__touchX=new float[32];
	float[] m__touchY=new float[32];
	public final void p_MouseEvent(int t_event,int t_data,float t_x,float t_y){
		int t_2=t_event;
		if(t_2==4){
			p_KeyEvent(1,1+t_data);
		}else{
			if(t_2==5){
				p_KeyEvent(2,1+t_data);
				return;
			}else{
				if(t_2==6){
				}else{
					return;
				}
			}
		}
		m__mouseX=t_x;
		m__mouseY=t_y;
		m__touchX[0]=t_x;
		m__touchY[0]=t_y;
	}
	public final void p_TouchEvent(int t_event,int t_data,float t_x,float t_y){
		int t_3=t_event;
		if(t_3==7){
			p_KeyEvent(1,384+t_data);
		}else{
			if(t_3==8){
				p_KeyEvent(2,384+t_data);
				return;
			}else{
				if(t_3==9){
				}else{
					return;
				}
			}
		}
		m__touchX[t_data]=t_x;
		m__touchY[t_data]=t_y;
		if(t_data==0){
			m__mouseX=t_x;
			m__mouseY=t_y;
		}
	}
	float m__accelX=.0f;
	float m__accelY=.0f;
	float m__accelZ=.0f;
	public final void p_MotionEvent(int t_event,int t_data,float t_x,float t_y,float t_z){
		int t_4=t_event;
		if(t_4==10){
		}else{
			return;
		}
		m__accelX=t_x;
		m__accelY=t_y;
		m__accelZ=t_z;
	}
	public final int p_KeyHit(int t_key){
		if(t_key>0 && t_key<512){
			return m__keyHit[t_key];
		}
		return 0;
	}
	public final boolean p_KeyDown(int t_key){
		if(t_key>0 && t_key<512){
			return m__keyDown[t_key];
		}
		return false;
	}
	public final float p_TouchX(int t_index){
		if(t_index>=0 && t_index<32){
			return m__touchX[t_index];
		}
		return 0.0f;
	}
	public final float p_TouchY(int t_index){
		if(t_index>=0 && t_index<32){
			return m__touchY[t_index];
		}
		return 0.0f;
	}
	public final int p_SetKeyboardEnabled(boolean t_enabled){
		BBGame.Game().SetKeyboardEnabled(t_enabled);
		return 1;
	}
	public final int p_GetChar(){
		if(m__charGet==m__charPut){
			return 0;
		}
		int t_chr=m__charQueue[m__charGet];
		m__charGet+=1;
		return t_chr;
	}
}
class c_JoyState extends Object{
	public final c_JoyState m_JoyState_new(){
		return this;
	}
	float[] m_joyx=new float[2];
	float[] m_joyy=new float[2];
	float[] m_joyz=new float[2];
	boolean[] m_buttons=new boolean[32];
}
class c_DisplayMode extends Object{
	int m__width=0;
	int m__height=0;
	public final c_DisplayMode m_DisplayMode_new(int t_width,int t_height){
		m__width=t_width;
		m__height=t_height;
		return this;
	}
	public final c_DisplayMode m_DisplayMode_new2(){
		return this;
	}
}
abstract class c_Map extends Object{
	public final c_Map m_Map_new(){
		return this;
	}
	c_Node m_root=null;
	public abstract int p_Compare(int t_lhs,int t_rhs);
	public final c_Node p_FindNode(int t_key){
		c_Node t_node=m_root;
		while((t_node)!=null){
			int t_cmp=p_Compare(t_key,t_node.m_key);
			if(t_cmp>0){
				t_node=t_node.m_right;
			}else{
				if(t_cmp<0){
					t_node=t_node.m_left;
				}else{
					return t_node;
				}
			}
		}
		return t_node;
	}
	public final boolean p_Contains(int t_key){
		return p_FindNode(t_key)!=null;
	}
	public final int p_RotateLeft(c_Node t_node){
		c_Node t_child=t_node.m_right;
		t_node.m_right=t_child.m_left;
		if((t_child.m_left)!=null){
			t_child.m_left.m_parent=t_node;
		}
		t_child.m_parent=t_node.m_parent;
		if((t_node.m_parent)!=null){
			if(t_node==t_node.m_parent.m_left){
				t_node.m_parent.m_left=t_child;
			}else{
				t_node.m_parent.m_right=t_child;
			}
		}else{
			m_root=t_child;
		}
		t_child.m_left=t_node;
		t_node.m_parent=t_child;
		return 0;
	}
	public final int p_RotateRight(c_Node t_node){
		c_Node t_child=t_node.m_left;
		t_node.m_left=t_child.m_right;
		if((t_child.m_right)!=null){
			t_child.m_right.m_parent=t_node;
		}
		t_child.m_parent=t_node.m_parent;
		if((t_node.m_parent)!=null){
			if(t_node==t_node.m_parent.m_right){
				t_node.m_parent.m_right=t_child;
			}else{
				t_node.m_parent.m_left=t_child;
			}
		}else{
			m_root=t_child;
		}
		t_child.m_right=t_node;
		t_node.m_parent=t_child;
		return 0;
	}
	public final int p_InsertFixup(c_Node t_node){
		while(((t_node.m_parent)!=null) && t_node.m_parent.m_color==-1 && ((t_node.m_parent.m_parent)!=null)){
			if(t_node.m_parent==t_node.m_parent.m_parent.m_left){
				c_Node t_uncle=t_node.m_parent.m_parent.m_right;
				if(((t_uncle)!=null) && t_uncle.m_color==-1){
					t_node.m_parent.m_color=1;
					t_uncle.m_color=1;
					t_uncle.m_parent.m_color=-1;
					t_node=t_uncle.m_parent;
				}else{
					if(t_node==t_node.m_parent.m_right){
						t_node=t_node.m_parent;
						p_RotateLeft(t_node);
					}
					t_node.m_parent.m_color=1;
					t_node.m_parent.m_parent.m_color=-1;
					p_RotateRight(t_node.m_parent.m_parent);
				}
			}else{
				c_Node t_uncle2=t_node.m_parent.m_parent.m_left;
				if(((t_uncle2)!=null) && t_uncle2.m_color==-1){
					t_node.m_parent.m_color=1;
					t_uncle2.m_color=1;
					t_uncle2.m_parent.m_color=-1;
					t_node=t_uncle2.m_parent;
				}else{
					if(t_node==t_node.m_parent.m_left){
						t_node=t_node.m_parent;
						p_RotateRight(t_node);
					}
					t_node.m_parent.m_color=1;
					t_node.m_parent.m_parent.m_color=-1;
					p_RotateLeft(t_node.m_parent.m_parent);
				}
			}
		}
		m_root.m_color=1;
		return 0;
	}
	public final boolean p_Set(int t_key,c_DisplayMode t_value){
		c_Node t_node=m_root;
		c_Node t_parent=null;
		int t_cmp=0;
		while((t_node)!=null){
			t_parent=t_node;
			t_cmp=p_Compare(t_key,t_node.m_key);
			if(t_cmp>0){
				t_node=t_node.m_right;
			}else{
				if(t_cmp<0){
					t_node=t_node.m_left;
				}else{
					t_node.m_value=t_value;
					return false;
				}
			}
		}
		t_node=(new c_Node()).m_Node_new(t_key,t_value,-1,t_parent);
		if((t_parent)!=null){
			if(t_cmp>0){
				t_parent.m_right=t_node;
			}else{
				t_parent.m_left=t_node;
			}
			p_InsertFixup(t_node);
		}else{
			m_root=t_node;
		}
		return true;
	}
	public final boolean p_Insert(int t_key,c_DisplayMode t_value){
		return p_Set(t_key,t_value);
	}
}
class c_IntMap extends c_Map{
	public final c_IntMap m_IntMap_new(){
		super.m_Map_new();
		return this;
	}
	public final int p_Compare(int t_lhs,int t_rhs){
		return t_lhs-t_rhs;
	}
}
class c_Stack extends Object{
	public final c_Stack m_Stack_new(){
		return this;
	}
	c_DisplayMode[] m_data=new c_DisplayMode[0];
	int m_length=0;
	public final c_Stack m_Stack_new2(c_DisplayMode[] t_data){
		this.m_data=((c_DisplayMode[])bb_std_lang.sliceArray(t_data,0));
		this.m_length=bb_std_lang.length(t_data);
		return this;
	}
	public final void p_Push(c_DisplayMode t_value){
		if(m_length==bb_std_lang.length(m_data)){
			m_data=(c_DisplayMode[])bb_std_lang.resize(m_data,m_length*2+10,c_DisplayMode.class);
		}
		m_data[m_length]=t_value;
		m_length+=1;
	}
	public final void p_Push2(c_DisplayMode[] t_values,int t_offset,int t_count){
		for(int t_i=0;t_i<t_count;t_i=t_i+1){
			p_Push(t_values[t_offset+t_i]);
		}
	}
	public final void p_Push3(c_DisplayMode[] t_values,int t_offset){
		p_Push2(t_values,t_offset,bb_std_lang.length(t_values)-t_offset);
	}
	public final c_DisplayMode[] p_ToArray(){
		c_DisplayMode[] t_t=new c_DisplayMode[m_length];
		for(int t_i=0;t_i<m_length;t_i=t_i+1){
			t_t[t_i]=m_data[t_i];
		}
		return t_t;
	}
}
class c_Node extends Object{
	int m_key=0;
	c_Node m_right=null;
	c_Node m_left=null;
	c_DisplayMode m_value=null;
	int m_color=0;
	c_Node m_parent=null;
	public final c_Node m_Node_new(int t_key,c_DisplayMode t_value,int t_color,c_Node t_parent){
		this.m_key=t_key;
		this.m_value=t_value;
		this.m_color=t_color;
		this.m_parent=t_parent;
		return this;
	}
	public final c_Node m_Node_new2(){
		return this;
	}
}
class c_BBGameEvent extends Object{
}
class c_Player extends Object{
	c_Vec2D m_position=null;
	c_Vec2D m_originalPosition=null;
	c_Vec2D m_velocity=null;
	c_CollisionRect m_box=null;
	float m_fireRate=.0f;
	float m_lastBullet=.0f;
	float m_speed=3.0f;
	c_Image[] m_playerImages=new c_Image[0];
	int[] m_colors=new int[]{0,0,0};
	int m_currentImg=0;
	public final c_Player m_Player_new(float t_x,float t_y,float t_color,int t_controls,float t_rate,c_Image[] t_imgArray,float t_moveSpeed){
		m_position=(new c_Vec2D()).m_Vec2D_new(t_x,t_y);
		m_originalPosition=(new c_Vec2D()).m_Vec2D_new(t_x,t_y);
		m_velocity=(new c_Vec2D()).m_Vec2D_new(0.0f,0.0f);
		m_box=(new c_CollisionRect()).m_CollisionRect_new(t_x,t_y,24,48);
		m_fireRate=t_rate;
		m_lastBullet=(float)(bb_app.g_Millisecs());
		m_speed=t_moveSpeed;
		m_playerImages=t_imgArray;
		if(t_color<1.0f){
			m_colors[0]=255;
			m_currentImg=0;
		}else{
			if(t_color<2.0f && t_color>=1.0f){
				m_colors[1]=255;
				m_currentImg=1;
			}else{
				if(t_color<3.0f && t_color>=2.0f){
					m_colors[2]=255;
					m_currentImg=2;
				}else{
					m_colors=new int[]{128,128,128};
				}
			}
		}
		return this;
	}
	public final c_Player m_Player_new2(){
		return this;
	}
	public final int p_SetSpeed(float t_newSpeed){
		m_speed=t_newSpeed;
		return 0;
	}
	float m_friction=0.3f;
	int m_controls=0;
	int m_leftKey=65;
	int m_rightKey=68;
	int m_upKey=87;
	int m_downKey=83;
	c_Vec2D m_topLeft=(new c_Vec2D()).m_Vec2D_new(0.0f,0.0f);
	c_Vec2D m_topRight=(new c_Vec2D()).m_Vec2D_new(0.0f,0.0f);
	c_Vec2D m_botLeft=(new c_Vec2D()).m_Vec2D_new(0.0f,0.0f);
	c_Vec2D m_botRight=(new c_Vec2D()).m_Vec2D_new(0.0f,0.0f);
	public final int p_UpdateCornerPoints(){
		m_topLeft.p_Set2(m_position.m_x,m_position.m_y);
		m_topRight.p_Set2(m_position.m_x+24.0f,m_position.m_y);
		m_botLeft.p_Set2(m_position.m_x,m_position.m_y+48.0f);
		m_botRight.p_Set2(m_position.m_x+24.0f,m_position.m_y+48.0f);
		return 0;
	}
	public final int p_SetPosition(float t_x,float t_y){
		m_position.p_Set2(t_x,t_y);
		p_UpdateCornerPoints();
		m_box.m_position.p_Set2(t_x,t_y);
		return 0;
	}
	public final int p_Update(){
		if(m_velocity.m_x>m_friction){
			m_velocity.m_x-=m_friction;
		}else{
			if(m_velocity.m_x<-m_friction){
				m_velocity.m_x+=m_friction;
			}else{
				m_velocity.m_x=0.0f;
			}
		}
		if(m_velocity.m_y>1.0f){
			m_velocity.m_y-=m_friction;
		}else{
			if(m_velocity.m_y<-1.0f){
				m_velocity.m_y+=m_friction;
			}else{
				m_velocity.m_y=0.0f;
			}
		}
		if(m_controls==0){
			if(((bb_input.g_KeyDown(m_leftKey))!=0) || ((bb_input.g_KeyDown(37))!=0)){
				m_velocity.m_x=-m_speed;
			}
			if(((bb_input.g_KeyDown(m_rightKey))!=0) || ((bb_input.g_KeyDown(39))!=0)){
				m_velocity.m_x=m_speed;
			}
			if(((bb_input.g_KeyDown(m_upKey))!=0) || ((bb_input.g_KeyDown(38))!=0)){
				m_velocity.m_y=-m_speed;
			}
			if(((bb_input.g_KeyDown(m_downKey))!=0) || ((bb_input.g_KeyDown(40))!=0)){
				m_velocity.m_y=m_speed;
			}
		}else{
			if(m_controls==1){
				if(((bb_input.g_TouchDown(0))!=0) && !((bb_input.g_TouchDown(1))!=0)){
					if(bb_input.g_TouchX(0)>m_position.m_x){
						m_velocity.m_x=m_speed;
					}else{
						if(bb_input.g_TouchX(0)<m_position.m_x){
							m_velocity.m_x=-m_speed;
						}
					}
				}
			}
		}
		p_SetPosition(m_position.m_x+m_velocity.m_x,m_position.m_y+m_velocity.m_y);
		return 0;
	}
	float m_bullet_speed=-8.0f;
	public final c_Bullet p_Fire(c_Image[] t_bulletImages){
		return (new c_Bullet()).m_Bullet_new(m_bullet_speed,m_position.m_x+12.0f,m_position.m_y,m_colors,t_bulletImages[m_currentImg]);
	}
	public final int p_ChangeColor2(){
		if(m_colors[0]==255){
			m_colors=new int[]{0,255,0};
			m_currentImg=1;
		}else{
			if(m_colors[1]==255){
				m_colors=new int[]{0,0,255};
				m_currentImg=2;
			}else{
				m_colors=new int[]{255,0,0};
				m_currentImg=0;
			}
		}
		return 0;
	}
	public final int p_Reset(){
		p_SetPosition(m_originalPosition.m_x,m_originalPosition.m_y);
		m_velocity.p_Set2(0.0f,0.0f);
		return 0;
	}
	public final int p_Draw(){
		bb_graphics.g_SetColor((float)(m_colors[0]),(float)(m_colors[1]),(float)(m_colors[2]));
		bb_graphics.g_DrawImage(m_playerImages[m_currentImg],m_position.m_x,m_position.m_y,0);
		return 0;
	}
}
class c_Vec2D extends Object{
	float m_x=.0f;
	float m_y=.0f;
	public final int p_Set2(float t_x,float t_y){
		this.m_x=t_x;
		this.m_y=t_y;
		return 0;
	}
	public final c_Vec2D m_Vec2D_new(float t_x,float t_y){
		p_Set2(t_x,t_y);
		return this;
	}
}
class c_CollisionRect extends Object{
	c_Vec2D m_position=null;
	int m_width=0;
	int m_height=0;
	public final c_CollisionRect m_CollisionRect_new(float t_x,float t_y,int t_wide,int t_high){
		m_position=(new c_Vec2D()).m_Vec2D_new(t_x,t_y);
		m_width=t_wide;
		m_height=t_high;
		return this;
	}
	public final c_CollisionRect m_CollisionRect_new2(){
		return this;
	}
}
class c_Bullet extends Object{
	c_Vec2D m_position=null;
	c_Vec2D m_velocity=null;
	c_CollisionRect m_box=null;
	public final int p_SetPosition(float t_x,float t_y){
		m_position.p_Set2(t_x,t_y);
		m_box.m_position.p_Set2(t_x,t_y);
		return 0;
	}
	public final int p_Update(){
		p_SetPosition(m_position.m_x+m_velocity.m_x,m_position.m_y+m_velocity.m_y);
		return 0;
	}
	int[] m_colors=new int[]{0,0,0};
	c_Image m_bulletImg=null;
	public final c_Bullet m_Bullet_new(float t_speed,float t_x,float t_y,int[] t_parent_colors,c_Image t_img){
		m_position=(new c_Vec2D()).m_Vec2D_new(t_x,t_y);
		m_velocity=(new c_Vec2D()).m_Vec2D_new(0.0f,t_speed);
		m_colors=t_parent_colors;
		m_bulletImg=t_img;
		m_box=(new c_CollisionRect()).m_CollisionRect_new(t_x,t_y,4,16);
		return this;
	}
	public final c_Bullet m_Bullet_new2(){
		return this;
	}
	public final int p_Draw(){
		bb_graphics.g_SetColor((float)(m_colors[0]),(float)(m_colors[1]),(float)(m_colors[2]));
		bb_graphics.g_DrawImage(m_bulletImg,m_position.m_x,m_position.m_y,0);
		return 0;
	}
}
class c_List extends Object{
	public final c_List m_List_new(){
		return this;
	}
	c_Node2 m__head=((new c_HeadNode()).m_HeadNode_new());
	public final c_Node2 p_AddLast(c_Bullet t_data){
		return (new c_Node2()).m_Node_new(m__head,m__head.m__pred,t_data);
	}
	public final c_List m_List_new2(c_Bullet[] t_data){
		c_Bullet[] t_=t_data;
		int t_2=0;
		while(t_2<bb_std_lang.length(t_)){
			c_Bullet t_t=t_[t_2];
			t_2=t_2+1;
			p_AddLast(t_t);
		}
		return this;
	}
	public final c_Enumerator p_ObjectEnumerator(){
		return (new c_Enumerator()).m_Enumerator_new(this);
	}
	public final boolean p_Equals(c_Bullet t_lhs,c_Bullet t_rhs){
		return t_lhs==t_rhs;
	}
	public final int p_RemoveEach(c_Bullet t_value){
		c_Node2 t_node=m__head.m__succ;
		while(t_node!=m__head){
			c_Node2 t_succ=t_node.m__succ;
			if(p_Equals(t_node.m__data,t_value)){
				t_node.p_Remove2();
			}
			t_node=t_succ;
		}
		return 0;
	}
	public final void p_Remove(c_Bullet t_value){
		p_RemoveEach(t_value);
	}
	public final int p_Clear(){
		m__head.m__succ=m__head;
		m__head.m__pred=m__head;
		return 0;
	}
}
class c_Node2 extends Object{
	c_Node2 m__succ=null;
	c_Node2 m__pred=null;
	c_Bullet m__data=null;
	public final c_Node2 m_Node_new(c_Node2 t_succ,c_Node2 t_pred,c_Bullet t_data){
		m__succ=t_succ;
		m__pred=t_pred;
		m__succ.m__pred=this;
		m__pred.m__succ=this;
		m__data=t_data;
		return this;
	}
	public final c_Node2 m_Node_new2(){
		return this;
	}
	public final int p_Remove2(){
		m__succ.m__pred=m__pred;
		m__pred.m__succ=m__succ;
		return 0;
	}
}
class c_HeadNode extends c_Node2{
	public final c_HeadNode m_HeadNode_new(){
		super.m_Node_new2();
		m__succ=(this);
		m__pred=(this);
		return this;
	}
}
class c_Enumerator extends Object{
	c_List m__list=null;
	c_Node2 m__curr=null;
	public final c_Enumerator m_Enumerator_new(c_List t_list){
		m__list=t_list;
		m__curr=t_list.m__head.m__succ;
		return this;
	}
	public final c_Enumerator m_Enumerator_new2(){
		return this;
	}
	public final boolean p_HasNext(){
		while(m__curr.m__succ.m__pred!=m__curr){
			m__curr=m__curr.m__succ;
		}
		return m__curr!=m__list.m__head;
	}
	public final c_Bullet p_NextObject(){
		c_Bullet t_data=m__curr.m__data;
		m__curr=m__curr.m__succ;
		return t_data;
	}
}
class c_Enemy extends Object{
	c_Vec2D m_position=null;
	c_Vec2D m_velocity=null;
	c_Image[] m_colorImages=new c_Image[0];
	c_CollisionRect m_box=null;
	int[] m_colors=new int[]{0,0,0};
	int m_currentImg=0;
	public final c_Enemy m_Enemy_new(float t_yspeed,float t_color,float t_x,float t_y,c_Image[] t_enemyImages){
		m_position=(new c_Vec2D()).m_Vec2D_new(t_x,t_y);
		m_velocity=(new c_Vec2D()).m_Vec2D_new(0.0f,t_yspeed);
		m_colorImages=t_enemyImages;
		m_box=(new c_CollisionRect()).m_CollisionRect_new(t_x,t_y,32,32);
		if(t_color<1.0f){
			m_colors[0]=255;
			m_currentImg=0;
		}else{
			if(t_color<2.0f && t_color>=1.0f){
				m_colors[1]=255;
				m_currentImg=1;
			}else{
				if(t_color<3.0f && t_color>=2.0f){
					m_colors[2]=255;
					m_currentImg=2;
				}else{
					m_colors=new int[]{128,128,128};
				}
			}
		}
		return this;
	}
	public final c_Enemy m_Enemy_new2(){
		return this;
	}
	public final int p_SetPosition(float t_x,float t_y){
		m_position.p_Set2(t_x,t_y);
		m_box.m_position.p_Set2(t_x,t_y);
		return 0;
	}
	public final int p_Update(){
		p_SetPosition(m_position.m_x+m_velocity.m_x,m_position.m_y+m_velocity.m_y);
		return 0;
	}
	public final int p_Draw(){
		bb_graphics.g_SetColor((float)(m_colors[0]),(float)(m_colors[1]),(float)(m_colors[2]));
		bb_graphics.g_DrawImage(m_colorImages[m_currentImg],m_position.m_x,m_position.m_y,0);
		return 0;
	}
}
class c_List2 extends Object{
	public final c_List2 m_List_new(){
		return this;
	}
	c_Node3 m__head=((new c_HeadNode2()).m_HeadNode_new());
	public final c_Node3 p_AddLast2(c_Enemy t_data){
		return (new c_Node3()).m_Node_new(m__head,m__head.m__pred,t_data);
	}
	public final c_List2 m_List_new2(c_Enemy[] t_data){
		c_Enemy[] t_=t_data;
		int t_2=0;
		while(t_2<bb_std_lang.length(t_)){
			c_Enemy t_t=t_[t_2];
			t_2=t_2+1;
			p_AddLast2(t_t);
		}
		return this;
	}
	public final c_Enumerator2 p_ObjectEnumerator(){
		return (new c_Enumerator2()).m_Enumerator_new(this);
	}
	public final boolean p_Equals2(c_Enemy t_lhs,c_Enemy t_rhs){
		return t_lhs==t_rhs;
	}
	public final int p_RemoveEach2(c_Enemy t_value){
		c_Node3 t_node=m__head.m__succ;
		while(t_node!=m__head){
			c_Node3 t_succ=t_node.m__succ;
			if(p_Equals2(t_node.m__data,t_value)){
				t_node.p_Remove2();
			}
			t_node=t_succ;
		}
		return 0;
	}
	public final void p_Remove3(c_Enemy t_value){
		p_RemoveEach2(t_value);
	}
	public final int p_Clear(){
		m__head.m__succ=m__head;
		m__head.m__pred=m__head;
		return 0;
	}
}
class c_Node3 extends Object{
	c_Node3 m__succ=null;
	c_Node3 m__pred=null;
	c_Enemy m__data=null;
	public final c_Node3 m_Node_new(c_Node3 t_succ,c_Node3 t_pred,c_Enemy t_data){
		m__succ=t_succ;
		m__pred=t_pred;
		m__succ.m__pred=this;
		m__pred.m__succ=this;
		m__data=t_data;
		return this;
	}
	public final c_Node3 m_Node_new2(){
		return this;
	}
	public final int p_Remove2(){
		m__succ.m__pred=m__pred;
		m__pred.m__succ=m__succ;
		return 0;
	}
}
class c_HeadNode2 extends c_Node3{
	public final c_HeadNode2 m_HeadNode_new(){
		super.m_Node_new2();
		m__succ=(this);
		m__pred=(this);
		return this;
	}
}
class c_Enumerator2 extends Object{
	c_List2 m__list=null;
	c_Node3 m__curr=null;
	public final c_Enumerator2 m_Enumerator_new(c_List2 t_list){
		m__list=t_list;
		m__curr=t_list.m__head.m__succ;
		return this;
	}
	public final c_Enumerator2 m_Enumerator_new2(){
		return this;
	}
	public final boolean p_HasNext(){
		while(m__curr.m__succ.m__pred!=m__curr){
			m__curr=m__curr.m__succ;
		}
		return m__curr!=m__list.m__head;
	}
	public final c_Enemy p_NextObject(){
		c_Enemy t_data=m__curr.m__data;
		m__curr=m__curr.m__succ;
		return t_data;
	}
}
interface c_IAsyncEventSource{
	public void p_UpdateAsyncEvents();
}
class c_HttpRequest extends Object implements c_IAsyncEventSource{
	public final c_HttpRequest m_HttpRequest_new(){
		return this;
	}
	BBHttpRequest m__req=null;
	c_IOnHttpRequestComplete m__onComplete=null;
	public final void p_Open(String t_req,String t_url,c_IOnHttpRequestComplete t_onComplete){
		if(((m__req)!=null) && m__req.IsRunning()){
			bb_std_lang.error("HttpRequest in progress");
		}
		m__req=(new BBHttpRequest());
		m__onComplete=t_onComplete;
		m__req.Open(t_req,t_url);
	}
	public final c_HttpRequest m_HttpRequest_new2(String t_req,String t_url,c_IOnHttpRequestComplete t_onComplete){
		p_Open(t_req,t_url,t_onComplete);
		return this;
	}
	public final void p_Send(){
		if(!((m__req)!=null)){
			bb_std_lang.error("HttpRequest not open");
		}
		if(m__req.IsRunning()){
			bb_std_lang.error("HttpRequest in progress");
		}
		bb_asyncevent.g_AddAsyncEventSource(this);
		m__req.Send();
	}
	public final void p_Send2(String t_data,String t_mimeType,String t_encoding){
		if(!((m__req)!=null)){
			bb_std_lang.error("HttpRequest not open");
		}
		if(m__req.IsRunning()){
			bb_std_lang.error("HttpRequest in progress");
		}
		if((t_mimeType).length()!=0){
			m__req.SetHeader("Content-Type",t_mimeType);
		}
		bb_asyncevent.g_AddAsyncEventSource(this);
		m__req.SendText(t_data,t_encoding);
	}
	public final void p_UpdateAsyncEvents(){
		if(m__req.IsRunning()){
			return;
		}
		bb_asyncevent.g_RemoveAsyncEventSource(this);
		m__onComplete.p_OnHttpRequestComplete(this);
	}
	public final int p_Status(){
		if(!((m__req)!=null)){
			bb_std_lang.error("HttpRequest not open");
		}
		return m__req.Status();
	}
	public final String p_ResponseText(){
		if(!((m__req)!=null)){
			bb_std_lang.error("HttpRequest not open");
		}
		return m__req.ResponseText();
	}
}
class c_Stack2 extends Object{
	public final c_Stack2 m_Stack_new(){
		return this;
	}
	c_IAsyncEventSource[] m_data=new c_IAsyncEventSource[0];
	int m_length=0;
	public final c_Stack2 m_Stack_new2(c_IAsyncEventSource[] t_data){
		this.m_data=((c_IAsyncEventSource[])bb_std_lang.sliceArray(t_data,0));
		this.m_length=bb_std_lang.length(t_data);
		return this;
	}
	public final boolean p_Equals3(c_IAsyncEventSource t_lhs,c_IAsyncEventSource t_rhs){
		return t_lhs==t_rhs;
	}
	public final boolean p_Contains2(c_IAsyncEventSource t_value){
		for(int t_i=0;t_i<m_length;t_i=t_i+1){
			if(p_Equals3(m_data[t_i],t_value)){
				return true;
			}
		}
		return false;
	}
	public final void p_Push4(c_IAsyncEventSource t_value){
		if(m_length==bb_std_lang.length(m_data)){
			m_data=(c_IAsyncEventSource[])bb_std_lang.resize(m_data,m_length*2+10,c_IAsyncEventSource.class);
		}
		m_data[m_length]=t_value;
		m_length+=1;
	}
	public final void p_Push5(c_IAsyncEventSource[] t_values,int t_offset,int t_count){
		for(int t_i=0;t_i<t_count;t_i=t_i+1){
			p_Push4(t_values[t_offset+t_i]);
		}
	}
	public final void p_Push6(c_IAsyncEventSource[] t_values,int t_offset){
		p_Push5(t_values,t_offset,bb_std_lang.length(t_values)-t_offset);
	}
	static c_IAsyncEventSource m_NIL;
	public final void p_Length(int t_newlength){
		if(t_newlength<m_length){
			for(int t_i=t_newlength;t_i<m_length;t_i=t_i+1){
				m_data[t_i]=m_NIL;
			}
		}else{
			if(t_newlength>bb_std_lang.length(m_data)){
				m_data=(c_IAsyncEventSource[])bb_std_lang.resize(m_data,bb_math.g_Max(m_length*2+10,t_newlength),c_IAsyncEventSource.class);
			}
		}
		m_length=t_newlength;
	}
	public final int p_Length2(){
		return m_length;
	}
	public final c_IAsyncEventSource p_Get(int t_index){
		return m_data[t_index];
	}
	public final void p_RemoveEach3(c_IAsyncEventSource t_value){
		int t_i=0;
		int t_j=m_length;
		while(t_i<m_length){
			if(!p_Equals3(m_data[t_i],t_value)){
				t_i+=1;
				continue;
			}
			int t_b=t_i;
			int t_e=t_i+1;
			while(t_e<m_length && p_Equals3(m_data[t_e],t_value)){
				t_e+=1;
			}
			while(t_e<m_length){
				m_data[t_b]=m_data[t_e];
				t_b+=1;
				t_e+=1;
			}
			m_length-=t_e-t_b;
			t_i+=1;
		}
		t_i=m_length;
		while(t_i<t_j){
			m_data[t_i]=m_NIL;
			t_i+=1;
		}
	}
}
class bb_Mojo{
}
class bb_asyncevent{
	static c_Stack2 g__sources;
	public static void g_AddAsyncEventSource(c_IAsyncEventSource t_source){
		if(bb_asyncevent.g__sources.p_Contains2(t_source)){
			bb_std_lang.error("Async event source is already active");
		}
		bb_asyncevent.g__sources.p_Push4(t_source);
	}
	static c_IAsyncEventSource g__current;
	public static int g_UpdateAsyncEvents(){
		if((bb_asyncevent.g__current)!=null){
			return 0;
		}
		int t_i=0;
		while(t_i<bb_asyncevent.g__sources.p_Length2()){
			bb_asyncevent.g__current=bb_asyncevent.g__sources.p_Get(t_i);
			bb_asyncevent.g__current.p_UpdateAsyncEvents();
			if((bb_asyncevent.g__current)!=null){
				t_i+=1;
			}
		}
		bb_asyncevent.g__current=null;
		return 0;
	}
	public static void g_RemoveAsyncEventSource(c_IAsyncEventSource t_source){
		if(t_source==bb_asyncevent.g__current){
			bb_asyncevent.g__current=null;
		}
		bb_asyncevent.g__sources.p_RemoveEach3(t_source);
	}
}
class bb_gametarget{
}
class bb_httprequest{
}
class bb_thread{
}
class bb_app{
	static c_App g__app;
	static c_GameDelegate g__delegate;
	static BBGame g__game;
	static int g__devWidth;
	static int g__devHeight;
	public static void g_ValidateDeviceWindow(boolean t_notifyApp){
		int t_w=bb_app.g__game.GetDeviceWidth();
		int t_h=bb_app.g__game.GetDeviceHeight();
		if(t_w==bb_app.g__devWidth && t_h==bb_app.g__devHeight){
			return;
		}
		bb_app.g__devWidth=t_w;
		bb_app.g__devHeight=t_h;
		if(t_notifyApp){
			bb_app.g__app.p_OnResize();
		}
	}
	static c_DisplayMode[] g__displayModes;
	static c_DisplayMode g__desktopMode;
	public static int g_DeviceWidth(){
		return bb_app.g__devWidth;
	}
	public static int g_DeviceHeight(){
		return bb_app.g__devHeight;
	}
	public static void g_EnumDisplayModes(){
		BBDisplayMode[] t_modes=bb_app.g__game.GetDisplayModes();
		c_IntMap t_mmap=(new c_IntMap()).m_IntMap_new();
		c_Stack t_mstack=(new c_Stack()).m_Stack_new();
		for(int t_i=0;t_i<bb_std_lang.length(t_modes);t_i=t_i+1){
			int t_w=t_modes[t_i].width;
			int t_h=t_modes[t_i].height;
			int t_size=t_w<<16|t_h;
			if(t_mmap.p_Contains(t_size)){
			}else{
				c_DisplayMode t_mode=(new c_DisplayMode()).m_DisplayMode_new(t_modes[t_i].width,t_modes[t_i].height);
				t_mmap.p_Insert(t_size,t_mode);
				t_mstack.p_Push(t_mode);
			}
		}
		bb_app.g__displayModes=t_mstack.p_ToArray();
		BBDisplayMode t_mode2=bb_app.g__game.GetDesktopMode();
		if((t_mode2)!=null){
			bb_app.g__desktopMode=(new c_DisplayMode()).m_DisplayMode_new(t_mode2.width,t_mode2.height);
		}else{
			bb_app.g__desktopMode=(new c_DisplayMode()).m_DisplayMode_new(bb_app.g_DeviceWidth(),bb_app.g_DeviceHeight());
		}
	}
	public static void g_EndApp(){
		bb_std_lang.error("");
	}
	static int g__updateRate;
	public static void g_SetUpdateRate(int t_hertz){
		bb_app.g__updateRate=t_hertz;
		bb_app.g__game.SetUpdateRate(t_hertz);
	}
	public static int g_Millisecs(){
		return bb_app.g__game.Millisecs();
	}
}
class bb_asyncimageloader{
}
class bb_asyncloaders{
}
class bb_asyncsoundloader{
}
class bb_audio{
	static gxtkAudio g_device;
	public static int g_SetAudioDevice(gxtkAudio t_dev){
		bb_audio.g_device=t_dev;
		return 0;
	}
}
class bb_audiodevice{
}
class bb_data{
	public static String g_FixDataPath(String t_path){
		int t_i=t_path.indexOf(":/",0);
		if(t_i!=-1 && t_path.indexOf("/",0)==t_i+1){
			return t_path;
		}
		if(t_path.startsWith("./") || t_path.startsWith("/")){
			return t_path;
		}
		return "monkey://data/"+t_path;
	}
}
class bb_driver{
}
class bb_graphics{
	static gxtkGraphics g_device;
	public static int g_SetGraphicsDevice(gxtkGraphics t_dev){
		bb_graphics.g_device=t_dev;
		return 0;
	}
	static c_GraphicsContext g_context;
	public static c_Image g_LoadImage(String t_path,int t_frameCount,int t_flags){
		gxtkSurface t_surf=bb_graphics.g_device.LoadSurface(bb_data.g_FixDataPath(t_path));
		if((t_surf)!=null){
			return ((new c_Image()).m_Image_new()).p_Init(t_surf,t_frameCount,t_flags);
		}
		return null;
	}
	public static c_Image g_LoadImage2(String t_path,int t_frameWidth,int t_frameHeight,int t_frameCount,int t_flags){
		gxtkSurface t_surf=bb_graphics.g_device.LoadSurface(bb_data.g_FixDataPath(t_path));
		if((t_surf)!=null){
			return ((new c_Image()).m_Image_new()).p_Init2(t_surf,0,0,t_frameWidth,t_frameHeight,t_frameCount,t_flags,null,0,0,t_surf.Width(),t_surf.Height());
		}
		return null;
	}
	public static int g_SetFont(c_Image t_font,int t_firstChar){
		if(!((t_font)!=null)){
			if(!((bb_graphics.g_context.m_defaultFont)!=null)){
				bb_graphics.g_context.m_defaultFont=bb_graphics.g_LoadImage("mojo_font.png",96,2);
			}
			t_font=bb_graphics.g_context.m_defaultFont;
			t_firstChar=32;
		}
		bb_graphics.g_context.m_font=t_font;
		bb_graphics.g_context.m_firstChar=t_firstChar;
		return 0;
	}
	static gxtkGraphics g_renderDevice;
	public static int g_SetMatrix(float t_ix,float t_iy,float t_jx,float t_jy,float t_tx,float t_ty){
		bb_graphics.g_context.m_ix=t_ix;
		bb_graphics.g_context.m_iy=t_iy;
		bb_graphics.g_context.m_jx=t_jx;
		bb_graphics.g_context.m_jy=t_jy;
		bb_graphics.g_context.m_tx=t_tx;
		bb_graphics.g_context.m_ty=t_ty;
		bb_graphics.g_context.m_tformed=((t_ix!=1.0f || t_iy!=0.0f || t_jx!=0.0f || t_jy!=1.0f || t_tx!=0.0f || t_ty!=0.0f)?1:0);
		bb_graphics.g_context.m_matDirty=1;
		return 0;
	}
	public static int g_SetMatrix2(float[] t_m){
		bb_graphics.g_SetMatrix(t_m[0],t_m[1],t_m[2],t_m[3],t_m[4],t_m[5]);
		return 0;
	}
	public static int g_SetColor(float t_r,float t_g,float t_b){
		bb_graphics.g_context.m_color_r=t_r;
		bb_graphics.g_context.m_color_g=t_g;
		bb_graphics.g_context.m_color_b=t_b;
		bb_graphics.g_renderDevice.SetColor(t_r,t_g,t_b);
		return 0;
	}
	public static int g_SetAlpha(float t_alpha){
		bb_graphics.g_context.m_alpha=t_alpha;
		bb_graphics.g_renderDevice.SetAlpha(t_alpha);
		return 0;
	}
	public static int g_SetBlend(int t_blend){
		bb_graphics.g_context.m_blend=t_blend;
		bb_graphics.g_renderDevice.SetBlend(t_blend);
		return 0;
	}
	public static int g_SetScissor(float t_x,float t_y,float t_width,float t_height){
		bb_graphics.g_context.m_scissor_x=t_x;
		bb_graphics.g_context.m_scissor_y=t_y;
		bb_graphics.g_context.m_scissor_width=t_width;
		bb_graphics.g_context.m_scissor_height=t_height;
		bb_graphics.g_renderDevice.SetScissor((int)(t_x),(int)(t_y),(int)(t_width),(int)(t_height));
		return 0;
	}
	public static int g_BeginRender(){
		bb_graphics.g_renderDevice=bb_graphics.g_device;
		bb_graphics.g_context.m_matrixSp=0;
		bb_graphics.g_SetMatrix(1.0f,0.0f,0.0f,1.0f,0.0f,0.0f);
		bb_graphics.g_SetColor(255.0f,255.0f,255.0f);
		bb_graphics.g_SetAlpha(1.0f);
		bb_graphics.g_SetBlend(0);
		bb_graphics.g_SetScissor(0.0f,0.0f,(float)(bb_app.g_DeviceWidth()),(float)(bb_app.g_DeviceHeight()));
		return 0;
	}
	public static int g_EndRender(){
		bb_graphics.g_renderDevice=null;
		return 0;
	}
	public static int g_Cls(float t_r,float t_g,float t_b){
		bb_graphics.g_renderDevice.Cls(t_r,t_g,t_b);
		return 0;
	}
	public static int g_DrawImage(c_Image t_image,float t_x,float t_y,int t_frame){
		c_Frame t_f=t_image.m_frames[t_frame];
		bb_graphics.g_context.p_Validate();
		if((t_image.m_flags&65536)!=0){
			bb_graphics.g_renderDevice.DrawSurface(t_image.m_surface,t_x-t_image.m_tx,t_y-t_image.m_ty);
		}else{
			bb_graphics.g_renderDevice.DrawSurface2(t_image.m_surface,t_x-t_image.m_tx,t_y-t_image.m_ty,t_f.m_x,t_f.m_y,t_image.m_width,t_image.m_height);
		}
		return 0;
	}
	public static int g_PushMatrix(){
		int t_sp=bb_graphics.g_context.m_matrixSp;
		if(t_sp==bb_std_lang.length(bb_graphics.g_context.m_matrixStack)){
			bb_graphics.g_context.m_matrixStack=(float[])bb_std_lang.resize(bb_graphics.g_context.m_matrixStack,t_sp*2,float.class);
		}
		bb_graphics.g_context.m_matrixStack[t_sp+0]=bb_graphics.g_context.m_ix;
		bb_graphics.g_context.m_matrixStack[t_sp+1]=bb_graphics.g_context.m_iy;
		bb_graphics.g_context.m_matrixStack[t_sp+2]=bb_graphics.g_context.m_jx;
		bb_graphics.g_context.m_matrixStack[t_sp+3]=bb_graphics.g_context.m_jy;
		bb_graphics.g_context.m_matrixStack[t_sp+4]=bb_graphics.g_context.m_tx;
		bb_graphics.g_context.m_matrixStack[t_sp+5]=bb_graphics.g_context.m_ty;
		bb_graphics.g_context.m_matrixSp=t_sp+6;
		return 0;
	}
	public static int g_Transform(float t_ix,float t_iy,float t_jx,float t_jy,float t_tx,float t_ty){
		float t_ix2=t_ix*bb_graphics.g_context.m_ix+t_iy*bb_graphics.g_context.m_jx;
		float t_iy2=t_ix*bb_graphics.g_context.m_iy+t_iy*bb_graphics.g_context.m_jy;
		float t_jx2=t_jx*bb_graphics.g_context.m_ix+t_jy*bb_graphics.g_context.m_jx;
		float t_jy2=t_jx*bb_graphics.g_context.m_iy+t_jy*bb_graphics.g_context.m_jy;
		float t_tx2=t_tx*bb_graphics.g_context.m_ix+t_ty*bb_graphics.g_context.m_jx+bb_graphics.g_context.m_tx;
		float t_ty2=t_tx*bb_graphics.g_context.m_iy+t_ty*bb_graphics.g_context.m_jy+bb_graphics.g_context.m_ty;
		bb_graphics.g_SetMatrix(t_ix2,t_iy2,t_jx2,t_jy2,t_tx2,t_ty2);
		return 0;
	}
	public static int g_Transform2(float[] t_m){
		bb_graphics.g_Transform(t_m[0],t_m[1],t_m[2],t_m[3],t_m[4],t_m[5]);
		return 0;
	}
	public static int g_Translate(float t_x,float t_y){
		bb_graphics.g_Transform(1.0f,0.0f,0.0f,1.0f,t_x,t_y);
		return 0;
	}
	public static int g_Rotate(float t_angle){
		bb_graphics.g_Transform((float)Math.cos((t_angle)*bb_std_lang.D2R),-(float)Math.sin((t_angle)*bb_std_lang.D2R),(float)Math.sin((t_angle)*bb_std_lang.D2R),(float)Math.cos((t_angle)*bb_std_lang.D2R),0.0f,0.0f);
		return 0;
	}
	public static int g_Scale(float t_x,float t_y){
		bb_graphics.g_Transform(t_x,0.0f,0.0f,t_y,0.0f,0.0f);
		return 0;
	}
	public static int g_PopMatrix(){
		int t_sp=bb_graphics.g_context.m_matrixSp-6;
		bb_graphics.g_SetMatrix(bb_graphics.g_context.m_matrixStack[t_sp+0],bb_graphics.g_context.m_matrixStack[t_sp+1],bb_graphics.g_context.m_matrixStack[t_sp+2],bb_graphics.g_context.m_matrixStack[t_sp+3],bb_graphics.g_context.m_matrixStack[t_sp+4],bb_graphics.g_context.m_matrixStack[t_sp+5]);
		bb_graphics.g_context.m_matrixSp=t_sp;
		return 0;
	}
	public static int g_DrawImage2(c_Image t_image,float t_x,float t_y,float t_rotation,float t_scaleX,float t_scaleY,int t_frame){
		c_Frame t_f=t_image.m_frames[t_frame];
		bb_graphics.g_PushMatrix();
		bb_graphics.g_Translate(t_x,t_y);
		bb_graphics.g_Rotate(t_rotation);
		bb_graphics.g_Scale(t_scaleX,t_scaleY);
		bb_graphics.g_Translate(-t_image.m_tx,-t_image.m_ty);
		bb_graphics.g_context.p_Validate();
		if((t_image.m_flags&65536)!=0){
			bb_graphics.g_renderDevice.DrawSurface(t_image.m_surface,0.0f,0.0f);
		}else{
			bb_graphics.g_renderDevice.DrawSurface2(t_image.m_surface,0.0f,0.0f,t_f.m_x,t_f.m_y,t_image.m_width,t_image.m_height);
		}
		bb_graphics.g_PopMatrix();
		return 0;
	}
	public static int g_DrawText(String t_text,float t_x,float t_y,float t_xalign,float t_yalign){
		if(!((bb_graphics.g_context.m_font)!=null)){
			return 0;
		}
		int t_w=bb_graphics.g_context.m_font.p_Width();
		int t_h=bb_graphics.g_context.m_font.p_Height();
		t_x-=(float)Math.floor((float)(t_w*t_text.length())*t_xalign);
		t_y-=(float)Math.floor((float)(t_h)*t_yalign);
		for(int t_i=0;t_i<t_text.length();t_i=t_i+1){
			int t_ch=(int)t_text.charAt(t_i)-bb_graphics.g_context.m_firstChar;
			if(t_ch>=0 && t_ch<bb_graphics.g_context.m_font.p_Frames()){
				bb_graphics.g_DrawImage(bb_graphics.g_context.m_font,t_x+(float)(t_i*t_w),t_y,t_ch);
			}
		}
		return 0;
	}
}
class bb_graphicsdevice{
}
class bb_input{
	static c_InputDevice g_device;
	public static int g_SetInputDevice(c_InputDevice t_dev){
		bb_input.g_device=t_dev;
		return 0;
	}
	public static int g_KeyHit(int t_key){
		return bb_input.g_device.p_KeyHit(t_key);
	}
	public static int g_TouchDown(int t_index){
		return ((bb_input.g_device.p_KeyDown(384+t_index))?1:0);
	}
	public static float g_TouchX(int t_index){
		return bb_input.g_device.p_TouchX(t_index);
	}
	public static float g_TouchY(int t_index){
		return bb_input.g_device.p_TouchY(t_index);
	}
	public static int g_EnableKeyboard(){
		return bb_input.g_device.p_SetKeyboardEnabled(true);
	}
	public static int g_GetChar(){
		return bb_input.g_device.p_GetChar();
	}
	public static int g_DisableKeyboard(){
		return bb_input.g_device.p_SetKeyboardEnabled(false);
	}
	public static int g_KeyDown(int t_key){
		return ((bb_input.g_device.p_KeyDown(t_key))?1:0);
	}
}
class bb_inputdevice{
}
class bb_keycodes{
}
class bb_boxes{
}
class bb_deque{
}
class bb_lang{
}
class bb_list{
}
class bb_map{
}
class bb_math{
	public static int g_Max(int t_x,int t_y){
		if(t_x>t_y){
			return t_x;
		}
		return t_y;
	}
	public static float g_Max2(float t_x,float t_y){
		if(t_x>t_y){
			return t_x;
		}
		return t_y;
	}
}
class bb_monkey{
}
class bb_random{
	static int g_Seed;
	public static float g_Rnd(){
		bb_random.g_Seed=bb_random.g_Seed*1664525+1013904223|0;
		return (float)(bb_random.g_Seed>>8&16777215)/16777216.0f;
	}
	public static float g_Rnd2(float t_low,float t_high){
		return bb_random.g_Rnd3(t_high-t_low)+t_low;
	}
	public static float g_Rnd3(float t_range){
		return bb_random.g_Rnd()*t_range;
	}
}
class bb_set{
}
class bb_stack{
}
class bb_monkeytarget{
}
class bb_{
	public static int bbMain(){
		(new c_PrismShipGame()).m_PrismShipGame_new();
		return 0;
	}
	public static int bbInit(){
		bb_app.g__app=null;
		bb_app.g__delegate=null;
		bb_app.g__game=BBGame.Game();
		bb_graphics.g_device=null;
		bb_graphics.g_context=(new c_GraphicsContext()).m_GraphicsContext_new();
		c_Image.m_DefaultFlags=0;
		bb_audio.g_device=null;
		bb_input.g_device=null;
		bb_app.g__devWidth=0;
		bb_app.g__devHeight=0;
		bb_app.g__displayModes=new c_DisplayMode[0];
		bb_app.g__desktopMode=null;
		bb_graphics.g_renderDevice=null;
		bb_app.g__updateRate=0;
		bb_random.g_Seed=1234;
		bb_asyncevent.g__sources=(new c_Stack2()).m_Stack_new();
		bb_asyncevent.g__current=null;
		c_Stack2.m_NIL=null;
		return 0;
	}
}
class bb_prismship{
}
//${TRANSCODE_END}

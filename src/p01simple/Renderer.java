package p01simple;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lwjglutils.OGLBuffers;
import lwjglutils.OGLTexture2D;

import lwjglutils.OGLUtils;

import lwjglutils.ShaderUtils;


import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL11;
import transforms.Camera;
import transforms.Mat4PerspRH;
import transforms.Mat4OrthoRH;
import transforms.Vec3D;


import java.io.IOException;
import java.util.Date;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20C.*;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;


/**
* 
* @author PGRF FIM UHK
* @version 2.0
* @since 2019-09-02
*/
public class Renderer extends AbstractRenderer{


    private OGLBuffers buffers;
    private int viewLocation,projectionLocation,shaderProgramMain, locWave,shaderProgramToroid,typeLocation, typeScale, projection = 1,toroid,juicer,ball,wave,coTex = 1,typecoTex,locTime;

    private Camera camera;
    private Mat4PerspRH persp;
    private Vec3D lightPosition;

    private boolean mousePressed;
    private double oldMx, oldMy;

    int anim = 0;
    float  scale=1f,time=1f,i=0.1f;
    private OGLTexture2D textureFire, textureBricks,textureMosaic,textureBall8,texturePavement;
    private Mat4OrthoRH ortho;


    @Override
    public void init() {
        OGLUtils.printOGLparameters();
        OGLUtils.printLWJLparameters();
        OGLUtils.printJAVAparameters();
        OGLUtils.shaderCheck();


        glClearColor(0.1f, 0.1f, 0.1f, 0f);
        glPolygonMode(GL_FRONT_AND_BACK, GL11.GL_FILL);
        shaderProgramMain = ShaderUtils.loadProgram("/main");
        viewLocation = glGetUniformLocation(shaderProgramMain, "view");
        projectionLocation = glGetUniformLocation(shaderProgramMain, "projection");
        typeLocation = glGetUniformLocation(shaderProgramMain, "type");
        typeScale = glGetUniformLocation(shaderProgramMain, "scale");
        typecoTex = glGetUniformLocation(shaderProgramMain, "coTex");
        locTime = glGetUniformLocation(shaderProgramMain, "time");



        camera = new Camera()
                .withPosition(new Vec3D(10, 10, 6))
                .withAzimuth(5 / 4f * Math.PI)
                .withZenith(-1 / 8f * Math.PI);

        persp = new Mat4PerspRH(
                Math.PI / 3f,
                LwjglWindow.HEIGHT / (float) LwjglWindow.WIDTH,
                0.1,
                50
        );

        ortho = new Mat4OrthoRH(
                20,
                20,
                0.1,
                50
        );

       lightPosition =new Vec3D(5.f,5.f,5.f);


    buffers = GridFactory.generateGrid(50,50);

        try {
            textureFire = new OGLTexture2D("./textures/mapFire.jpg");
            textureBricks = new OGLTexture2D("./textures/bricksn.png");
            textureMosaic = new OGLTexture2D("./textures/mosaic.jpg");
            textureBall8 = new OGLTexture2D("./textures/pool/Ball8.jpg");
            texturePavement = new OGLTexture2D("./pavementHigh.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void display() {

        glUseProgram(shaderProgramMain);


        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);


        float[] ambientLight = {0f, 0f, 1f,0f };

        float[] specularLight = {1f, 0f, 0f,0f };

        float[] diffuseLight = { 1f,0f,0f,0f };


        textureFire.bind(shaderProgramMain,"textureFire",0);
        textureBricks.bind(shaderProgramMain,"textureBricks",1);
        textureMosaic.bind(shaderProgramMain,"textureMosaic",2);
        textureBall8.bind(shaderProgramMain,"textureBall8",4);
        texturePavement.bind(shaderProgramMain,"texturePavement",3);

        glUniform1f(typeScale,scale);
        glUniform1f(typecoTex,coTex);
        glUniform1f(locTime,time);
        glUniformMatrix4fv(viewLocation,false, camera.getViewMatrix().floatArray());
        glUniform3f (typeLocation,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());


        if(projection == 1 ){
        glUniformMatrix4fv(projectionLocation, false,persp.floatArray());}
        else if(projection == 2){
        glUniformMatrix4fv(projectionLocation, false,ortho.floatArray());}

      //  glUniform1f(typeLocation,0f);
       // buffers.draw(GL_TRIANGLES, shaderProgramMain);
      //  glUniform1f(typeLocation,0.1f);
     //   buffers.draw(GL_TRIANGLES, shaderProgramMain);
       if (wave == 1){
        glUniform1f(typeLocation,1f);
        buffers.draw(GL_TRIANGLES, shaderProgramMain);}

        if (toroid == 1){
        glUniform1f(typeLocation,2f);
        buffers.draw(GL_TRIANGLES, shaderProgramMain);}

        if (juicer == 1){
        glUniform1f(typeLocation,3f);
        buffers.draw(GL_TRIANGLES, shaderProgramMain);}

        if (ball == 1){
        glUniform1f(typeLocation,4f);
        buffers.draw(GL_TRIANGLES, shaderProgramMain);}

      //  glUniform1f(typeLocation,5f);
      //  buffers.draw(GL_TRIANGLES, shaderProgramMain);
      //  glUniform1f(typeLocation,6f);
       // buffers.draw(GL_TRIANGLES, shaderProgramMain);
        //Date date = new Date();
       // time = date.;
if (anim==1) {
    if (i < 3f) {
        time = time + 0.01f;
        i = i + 0.01f;
    } else if (i>=3f && i<=7f) {
        time = time - 0.01f;
        i = i + 0.01f;
    } else {i = 0.01f;
            time = 0f;}
}

//else
//{
 //   i = 0f;
//}

    }


    private GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback () {
		@Override
		public void invoke(long window, int button, int action, int mods) {
            if (button == GLFW_MOUSE_BUTTON_LEFT) {
                double[] xPos = new double[1];
                double[] yPos = new double[1];
               glfwGetCursorPos(window, xPos, yPos);
               oldMx = xPos[0];
               oldMy = yPos[0];
               mousePressed = action == GLFW_PRESS;
            }

		}

	};

    private GLFWCursorPosCallback cursorPosCallback = new GLFWCursorPosCallback() {
        @Override
        public void invoke(long window, double x, double y) {

            if (mousePressed) {
                camera = camera.addAzimuth((Math.PI / 2 * (oldMx - x)/LwjglWindow.WIDTH));
                camera = camera.addZenith((Math.PI / 2 * (oldMy - y)/LwjglWindow.HEIGHT));
                oldMx = x;
                oldMy = y;
            }
        }
    };

    @Override
    public GLFWMouseButtonCallback getMouseCallback() {
       return mouseButtonCallback;

    }
    @Override
    public GLFWCursorPosCallback getCursorCallback() {
      return cursorPosCallback;
        }

    private GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (action == GLFW_PRESS || action == GLFW_REPEAT){
                switch (key) {
                    case GLFW_KEY_W:
                        camera = camera.forward(1);
                        break;
                    case GLFW_KEY_D:
                        camera = camera.right(1);
                        break;
                    case GLFW_KEY_S:
                        camera = camera.backward(1);
                        break;
                    case GLFW_KEY_A:
                        camera = camera.left(1);
                        break;
                    case GLFW_KEY_LEFT_CONTROL:
                        camera = camera.down(1);
                        break;
                    case GLFW_KEY_LEFT_SHIFT:
                        camera = camera.up(1);
                        break;
                    case GLFW_KEY_SPACE:
                        camera = camera.withFirstPerson(!camera.getFirstPerson());
                        break;
                    case GLFW_KEY_R:
                        camera= camera.mulRadius(0.9f);
                        break;
                    case GLFW_KEY_F:
                        camera = camera.mulRadius(1.1f);
                        break;
                    case GLFW_KEY_P:
                        if (projection == 1){
                        projection = 2;}
                        else {projection = 1;}
                        break;
                    case GLFW_KEY_C:
                        if (coTex == 1){
                            coTex = 0;}
                        else {coTex = 1;}
                        break;
                    case GLFW_KEY_I:
                        if (anim == 1){
                            anim = 0;}
                        else {anim  = 1;}
                        break;

                    case GLFW_KEY_1:
                        if (wave == 1){
                            wave = 0;}
                        else {wave  = 1;}
                        break;
                    case GLFW_KEY_2:
                        if (toroid == 1){
                            toroid = 0;}
                        else {toroid  = 1;}
                        break;
                    case GLFW_KEY_3:
                        if (juicer == 1){
                            juicer = 0;}
                        else {juicer  = 1;}
                        break;
                    case GLFW_KEY_4:
                        if (ball == 1){
                            ball = 0;}
                        else {ball  = 1;}
                        break;

                }
            }


        }
    };
    @Override
    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    protected GLFWScrollCallback scrollCallback = new GLFWScrollCallback() {
        @Override public void invoke (long window, double dx, double dy) {

            scale = (scale + (float) dy)/1;


        }
    };
    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

}


 


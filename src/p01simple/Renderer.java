package p01simple;

import lwjglutils.OGLBuffers;
import lwjglutils.OGLTexture2D;

import lwjglutils.OGLUtils;

import lwjglutils.ShaderUtils;


import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL11;
import transforms.*;


import java.io.IOException;
import java.util.concurrent.TimeUnit;


import static java.lang.System.currentTimeMillis;
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
public class Renderer extends p01simple.AbstractRenderer {


    private OGLBuffers buffers,
            buffersSPSolid1,buffersSPSolid2,buffersCLSolid1,buffersCLSolid2,buffersCASolid1,buffersCASolid2;
    private int viewLocation, viewLocationSPSolid1,viewLocationSPSolid2,viewLocationCLSolid1,
            viewLocationCLSolid2,viewLocationCASolid1,viewLocationCASolid2,
            viewPositionSPSolid1,viewPositionSPSolid2,viewPositionCLSolid1,
            viewPositionCLSolid2,viewPositionCASolid1,viewPositionCASolid2,
            lightLocation, lightLocationSPSolid1,lightLocationSPSolid2,lightLocationCLSolid1,
            lightLocationCLSolid2,lightLocationCASolid1,lightLocationCASolid2,
            projectionLocation,projectionLocationSPSolid1,projectionLocationSPSolid2,projectionLocationCLSolid1,
            projectionLocationCLSolid2,projectionLocationCASolid1,projectionLocationCASolid2,
            shaderProgramMain,
            shaderProgramSPSolid1,shaderProgramSPSolid2,shaderProgramCLSolid1,shaderProgramCLSolid2,shaderProgramCASolid1,
            shaderProgramCASolid2,

            typeScale, projection = 1,SPSolid1,SPSolid2,CLSolid1, CLSolid2,CASolid1,CASolid2,
            coTex = 1,typecoTex,locTime,coord,deep,surfaceColor, norm,textoreCoord,distance,
                    lightAmbCA1,lightAmbCA2,lightAmbSP1,lightAmbSP2,lightAmbCL1,lightAmbCL2,
            lightPos = 1,surface,surfaceCA1=0,surfaceCA2=0,surfaceCL1=0,surfaceCL2=0,surfaceSP1=0,surfaceSP2=0,
            scaleMSPSolid1,scaleMSPSolid2,scaleMCLSolid1,scaleMCLSolid2,scaleMCASolid1,scaleMCASolid2,
            rotateMSPSolid1,rotateMSPSolid2,rotateMCLSolid1,rotateMCLSolid2,rotateMCASolid1,rotateMCASolid2;

    private Camera camera;
    private Mat4PerspRH persp;
    private Vec3D lightPosition, lightAmbient;

    private  Mat4Scale scaleMat;
    private  Mat4RotXYZ rotateMat;

    private boolean mousePressed;
    private double oldMx, oldMy, ofst;

    int anim = 0, wire = 0;
    float  time=1f,i=0.1f;
    double scale = 1;
    private OGLTexture2D textureFire, textureBricks,textureMosaic,textureBall8,texturePavement;
    private Mat4OrthoRH ortho;


    @Override
    public void init() {
        OGLUtils.printOGLparameters();
        OGLUtils.printLWJLparameters();
        OGLUtils.printJAVAparameters();
        OGLUtils.shaderCheck();
        glEnable(GL_DEPTH_TEST);

        glClearColor(0.120f, 0.120f, 0.120f, 1f);

        shaderProgramMain = ShaderUtils.loadProgram("/main");
        shaderProgramSPSolid1 = ShaderUtils.loadProgram("/SPSolid1");
        shaderProgramSPSolid2 = ShaderUtils.loadProgram("/SPSolid2");
        shaderProgramCLSolid1 = ShaderUtils.loadProgram("/CLSolid1");
        shaderProgramCLSolid2 = ShaderUtils.loadProgram("/CLSolid2");
        shaderProgramCASolid1 = ShaderUtils.loadProgram("/CASolid1");
        shaderProgramCASolid2 = ShaderUtils.loadProgram("/CASolid2");

        viewLocation = glGetUniformLocation(shaderProgramMain, "view");
        viewLocationSPSolid1= glGetUniformLocation(shaderProgramSPSolid1, "viewSPSolid1");
        viewLocationSPSolid2= glGetUniformLocation(shaderProgramSPSolid2, "viewSPSolid2");
        viewLocationCLSolid1= glGetUniformLocation(shaderProgramCLSolid1, "viewCLSolid1");
        viewLocationCLSolid2= glGetUniformLocation(shaderProgramCLSolid2, "viewCLSolid2");
        viewLocationCASolid1= glGetUniformLocation(shaderProgramCASolid1, "viewCASolid1");
        viewLocationCASolid2= glGetUniformLocation(shaderProgramCASolid2, "viewCASolid2");

        viewPositionSPSolid1= glGetUniformLocation(shaderProgramSPSolid1, "viewPositionSPSolid1");
        viewPositionSPSolid2= glGetUniformLocation(shaderProgramSPSolid2, "viewPositionSPSolid2");
        viewPositionCLSolid1= glGetUniformLocation(shaderProgramCLSolid1, "viewPositionCLSolid1");
        viewPositionCLSolid2= glGetUniformLocation(shaderProgramCLSolid2, "viewPositionCLSolid2");
        viewPositionCASolid1= glGetUniformLocation(shaderProgramCASolid1, "viewPositionCASolid1");
        viewPositionCASolid2= glGetUniformLocation(shaderProgramCASolid2, "viewPositionCASolid2");

     //   lightLocation = glGetUniformLocation(shaderProgramMain, "light");
        lightLocationSPSolid1= glGetUniformLocation(shaderProgramSPSolid1, "lightSPSolid1");
        lightLocationSPSolid2= glGetUniformLocation(shaderProgramSPSolid2, "lightSPSolid2");
        lightLocationCLSolid1= glGetUniformLocation(shaderProgramCLSolid1, "lightCLSolid1");
        lightLocationCLSolid2= glGetUniformLocation(shaderProgramCLSolid2, "lightCLSolid2");
        lightLocationCASolid1= glGetUniformLocation(shaderProgramCASolid1, "lightCASolid1");
        lightLocationCASolid2= glGetUniformLocation(shaderProgramCASolid2, "lightCASolid2");



     //   projectionLocation = glGetUniformLocation(shaderProgramMain, "projection");
        projectionLocationSPSolid1 = glGetUniformLocation(shaderProgramSPSolid1, "projectionSPSolid1");
        projectionLocationSPSolid2 = glGetUniformLocation(shaderProgramSPSolid2, "projectionSPSolid2");
        projectionLocationCLSolid1 = glGetUniformLocation(shaderProgramCLSolid1, "projectionCLSolid1");
        projectionLocationCLSolid2 = glGetUniformLocation(shaderProgramCLSolid2, "projectionCLSolid2");
        projectionLocationCASolid1 = glGetUniformLocation(shaderProgramCASolid1, "projectionCASolid1");
        projectionLocationCASolid2 = glGetUniformLocation(shaderProgramCASolid2, "projectionCASolid2");


        scaleMSPSolid1 = glGetUniformLocation(shaderProgramSPSolid1, "scaleMSPSolid1");
        scaleMSPSolid2 = glGetUniformLocation(shaderProgramSPSolid2, "scaleMSPSolid2");
        scaleMCLSolid1 = glGetUniformLocation(shaderProgramCLSolid1, "scaleMCLSolid1");
        scaleMCLSolid2 = glGetUniformLocation(shaderProgramCLSolid2, "scaleMCLSolid2");
        scaleMCASolid1 = glGetUniformLocation(shaderProgramCASolid1, "scaleMCASolid1");
        scaleMCASolid2 = glGetUniformLocation(shaderProgramCASolid2, "scaleMCASolid2");

        rotateMSPSolid1 = glGetUniformLocation(shaderProgramSPSolid1, "rotateMSPSolid1");
        rotateMSPSolid2 = glGetUniformLocation(shaderProgramSPSolid2, "rotateMSPSolid2");
        rotateMCLSolid1 = glGetUniformLocation(shaderProgramCLSolid1, "rotateMCLSolid1");
        rotateMCLSolid2 = glGetUniformLocation(shaderProgramCLSolid2, "rotateMCLSolid2");
        rotateMCASolid1 = glGetUniformLocation(shaderProgramCASolid1, "rotateMCASolid1");
        rotateMCASolid2 = glGetUniformLocation(shaderProgramCASolid2, "rotateMCASolid2");

        typecoTex = glGetUniformLocation(shaderProgramCASolid1, "coTex");

        surfaceCA1 = glGetUniformLocation(shaderProgramCASolid1, "surfaceCA1");
        surfaceCA2 = glGetUniformLocation(shaderProgramCASolid2, "surfaceCA2");
        surfaceCL1 = glGetUniformLocation(shaderProgramCLSolid1, "surfaceCL1");
        surfaceCL2 = glGetUniformLocation(shaderProgramCLSolid2, "surfaceCL2");
        surfaceSP1 = glGetUniformLocation(shaderProgramSPSolid1, "surfaceSP1");
        surfaceSP2 = glGetUniformLocation(shaderProgramSPSolid2, "surfaceSP2");

        lightAmbCA1 = glGetUniformLocation(shaderProgramCASolid1, "lightAmbCA1");
        lightAmbCA2 = glGetUniformLocation(shaderProgramCASolid2, "lightAmbCA2");
        lightAmbCL1 = glGetUniformLocation(shaderProgramCLSolid1, "lightAmbCL1");
        lightAmbCL2 = glGetUniformLocation(shaderProgramCLSolid2, "lightAmbCL2");
        lightAmbSP1 = glGetUniformLocation(shaderProgramSPSolid1, "lightAmbSP1");
        lightAmbSP2 = glGetUniformLocation(shaderProgramSPSolid2, "lightAmbSP2");


        camera = new Camera()
                //    .withPosition(new Vec3D(10, 10, 6))
                .withPosition(new Vec3D(25, 8, 6))
                .withAzimuth(5 / 1f * Math.PI)
                .withZenith(-1 / 30f * Math.PI);

        persp = new Mat4PerspRH(
                Math.PI / 3f,
                p01simple.LwjglWindow.HEIGHT / (float) p01simple.LwjglWindow.WIDTH,
                0.1,
                100
        );

        ortho = new Mat4OrthoRH(
                20,
                20,
                0.1,
                50
        );

        lightAmbient = new Vec3D(1.f, 1.f, 1.f);

        buffers = p01simple.GridFactory.generateGrid(100,100);
        buffersSPSolid1 = p01simple.GridFactory.generateGrid(100,100);
        buffersSPSolid2 = p01simple.GridFactory.generateGrid(100,100);
        buffersCLSolid1 = p01simple.GridFactory.generateGrid(100,100);
        buffersCLSolid2 = p01simple.GridFactory.generateGrid(100,100);
        buffersCASolid1 = p01simple.GridFactory.generateGrid(100,100);
        buffersCASolid2 = p01simple.GridFactory.generateGrid(100,100);

        try {
            textureFire = new OGLTexture2D("./textures/mapFire.jpg");
            textureBricks = new OGLTexture2D("./textures/bricks.jpg");
            textureMosaic = new OGLTexture2D("./textures/mosaic.jpg");
            textureBall8 = new OGLTexture2D("./textures/pool/Ball8.jpg");
            texturePavement = new OGLTexture2D("./pavementHigh.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void display() {

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (wire == 0){
            glPolygonMode(GL_FRONT_AND_BACK, GL11.GL_FILL);}
        else {
            glPolygonMode(GL_FRONT_AND_BACK, GL11.GL_LINE);}

        scaleMat = new Mat4Scale(scale,scale,scale);
        rotateMat = new Mat4RotXYZ(ofst,ofst,ofst);
        if (lightPos == 1) {
            lightPosition = new Vec3D(0, 0, 10);
        } else {
            lightPosition = new Vec3D(0, 10, 10);
        }


        //////////////////sharder Main

        glUseProgram(shaderProgramMain);

        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocation, false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocation, false,ortho.floatArray());}

        glUniformMatrix4fv(viewLocation,false, camera.getViewMatrix().floatArray());
        glUniform3f (lightLocation,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());



        //////////////////////// shader CASolid1
        glUseProgram(shaderProgramCASolid1);
        glUniform1f(typecoTex,coTex);
        glUniform1f(locTime,time);
        glUniform1i(surfaceCA1,surface);
        textureFire.bind(shaderProgramCASolid1,"textureFire",0);

        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationCASolid1 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationCASolid1, false,ortho.floatArray());}

        glUniformMatrix4fv(viewLocationCASolid1,false, camera.getViewMatrix().floatArray());
        glUniform3f(viewPositionCASolid1, (float)camera.getPosition().getX(),(float)camera.getPosition().getY(),(float)camera.getPosition().getZ());
        glUniformMatrix4fv(scaleMCASolid1,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMCASolid1,false,rotateMat.floatArray());
        glUniform3f (lightLocationCASolid1,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbCA1,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());

        if (CASolid1 == 1){
            buffersCASolid1.draw(GL_TRIANGLES, shaderProgramCASolid1);
        }
        //////////////////////// shader CASolid2
        glUseProgram(shaderProgramCASolid2);

        glUniform1f(typecoTex,coTex);
        glUniform1f(locTime,time);
        glUniform1i(surfaceCA2,surface);
        textureBricks.bind(shaderProgramCASolid2,"textureBricks",0);

        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationCASolid2 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationCASolid2, false,ortho.floatArray());}

        glUniformMatrix4fv(viewLocationCASolid2,false, camera.getViewMatrix().floatArray());
        glUniformMatrix4fv(scaleMCASolid2,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMCASolid2,false,rotateMat.floatArray());
        glUniform3f (lightLocationCASolid2,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbCA2,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());
        if (CASolid2 == 1){
            buffersCASolid2.draw(GL_TRIANGLES, shaderProgramCASolid2);
        }
      /////////////////////////////  // shader CLSolid1
        glUseProgram(shaderProgramCLSolid1);

        glUniform1f(typecoTex,coTex);
        glUniform1f(locTime,time);
        glUniform1i(surfaceCL1,surface);
        textureMosaic.bind(shaderProgramCLSolid1,"textureMosaic",0);
        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationCLSolid1 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationCLSolid1, false,ortho.floatArray());}
        glUniformMatrix4fv(viewLocationCLSolid1,false, camera.getViewMatrix().floatArray());
        glUniformMatrix4fv(scaleMCLSolid1,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMCLSolid1,false,rotateMat.floatArray());
        glUniform3f (lightLocationCLSolid1,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbCL1,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());
        if (CLSolid1 == 1){
            buffersCLSolid1.draw(GL_TRIANGLES, shaderProgramCLSolid1);
        }
        // shader CLSolid2
        glUseProgram(shaderProgramCLSolid2);

        glUniform1f(typecoTex,coTex);
        glUniform1f(locTime,time);
        glUniform1i(surfaceCL2,surface);
        textureMosaic.bind(shaderProgramCLSolid2,"textureMosaic",0);
        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationCLSolid2 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationCLSolid2, false,ortho.floatArray());}

        glUniformMatrix4fv(viewLocationCLSolid2,false, camera.getViewMatrix().floatArray());
        glUniformMatrix4fv(scaleMCLSolid2,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMCLSolid2,false,rotateMat.floatArray());
        glUniform3f (lightLocationCLSolid2,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbCL2,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());
        if (CLSolid2 == 1){
            buffersCLSolid2.draw(GL_TRIANGLES, shaderProgramCLSolid2);
        }
        ///////////////////////////// shader SPSolid1
       glUseProgram(shaderProgramSPSolid1);

        glUniform1f(typecoTex,coTex);
        glUniform1f(locTime,time);
        glUniform1i(surfaceSP1,surface);
        texturePavement.bind(shaderProgramSPSolid1,"texturePavement",0);

        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationSPSolid1 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationSPSolid1, false,ortho.floatArray());}

        glUniformMatrix4fv(viewLocationSPSolid1,false, camera.getViewMatrix().floatArray());
        glUniformMatrix4fv(scaleMSPSolid1,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMSPSolid1,false,rotateMat.floatArray());
        glUniform3f (lightLocationSPSolid1,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbSP1,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());
        if (SPSolid1 == 1){
            buffersSPSolid1.draw(GL_TRIANGLES, shaderProgramSPSolid1);
        }

       /////////////////////// shader SPSolid2
        glUseProgram(shaderProgramSPSolid2);

        glUniform1f(typecoTex,coTex);
        glUniform1f(locTime,time);
        glUniform1i(surfaceSP2,surface);
        textureFire.bind(shaderProgramSPSolid2,"textureFire",0);

        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationSPSolid2 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationSPSolid2, false,ortho.floatArray());}
        glUniformMatrix4fv(scaleMSPSolid2,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMSPSolid2,false,rotateMat.floatArray());
        glUniformMatrix4fv(viewLocationSPSolid2,false, camera.getViewMatrix().floatArray());
        glUniform3f (lightLocationSPSolid2,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbSP2,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());
        if (SPSolid2 == 1){
            buffersSPSolid2.draw(GL_TRIANGLES, shaderProgramSPSolid2);
        }

if (anim == 1) {

    ofst = Math.cos(TimeUnit.MILLISECONDS.toSeconds( (int) currentTimeMillis()*5));

}
       /* if (anim==1) {
            if (i < 3f) {
                time = Math.cos(time + 0.01);
                i = i + 0.01f;
            } else if (i>=3f && i<=7f) {
                time = time - 0.01f;
                i = i + 0.01f;
            } else {i = 0.01f;
                time = 0f;}
        } */

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
                camera = camera.addAzimuth((Math.PI / 2 * (oldMx - x)/ p01simple.LwjglWindow.WIDTH));
                camera = camera.addZenith((Math.PI / 2 * (oldMy - y)/ p01simple.LwjglWindow.HEIGHT));
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
                    case GLFW_KEY_M:
                        if (wire == 1){
                            wire = 0;}
                        else {wire  = 1;}
                        break;
                /*    case GLFW_KEY_R:
                        if (lightPos == 1){
                            lightPos = 0;}
                        else {lightPos  = 1;}
                        break;*/

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
                    case GLFW_KEY_I:
                        if (anim == 1){
                            anim = 0;}
                        else {anim  = 1;}
                    case GLFW_KEY_O:
                        if (lightPos == 1){
                            lightPos = 0;}
                        else {lightPos  = 1;}
                        break;
                    case GLFW_KEY_T:
                        surface = 0;
                        break;
                    case GLFW_KEY_X:
                        surface = 1;
                        break;
                    case GLFW_KEY_V:
                        surface = 2;
                        break;
                    case GLFW_KEY_B:
                        surface = 3;
                        break;
                    case GLFW_KEY_C:
                        surface = 4;
                        break;
                    case GLFW_KEY_N:
                        surface = 5;
                        break;
                    case GLFW_KEY_Y:
                        surface = 6;
                        break;
                    case GLFW_KEY_L:
                        surface = 7;
                        break;
                    case GLFW_KEY_1:
                        if (CASolid1 == 1){
                            CASolid1 = 0;}
                        else {CASolid1  = 1;}
                        break;
                    case GLFW_KEY_2:
                        if (CASolid2 == 1){
                            CASolid2 = 0;}
                        else {CASolid2  = 1;}
                        break;
                    case GLFW_KEY_3:
                        if (CLSolid1 == 1){
                            CLSolid1 = 0;}
                        else {CLSolid1  = 1;}
                        break;
                    case GLFW_KEY_4:
                        if (CLSolid2 == 1){
                            CLSolid2 = 0;}
                        else {CLSolid2  = 1;}
                        break;
                    case GLFW_KEY_5:
                        if (SPSolid1 == 1){
                            SPSolid1 = 0;}
                        else {SPSolid1  = 1;}
                        break;
                    case GLFW_KEY_6:
                        if (SPSolid2 == 1){
                            SPSolid2  = 0;}
                        else {SPSolid2   = 1;}
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

            scale = (scale + (float) dy);
            if (scale >= 0) {

            } else if (scale <=0){
                scale = 0;
            }

        }
    };
    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }



}




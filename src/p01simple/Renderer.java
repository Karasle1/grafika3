package p01simple;

import lwjglutils.*;
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
            buffersSPSolid1,buffersCASolid1,buffersCASolid2,buffersSky;
    private int viewLocation, viewLocationSPSolid1,viewLocationCASolid2, viewLocationCASolid1,
            viewPositionSPSolid1, viewPositionCASolid1,viewPositionCASolid2,
            lightLocation, lightLocationSPSolid1,lightLocationCASolid2,
          lightLocationCASolid1,
            projectionLocation,projectionLocationSPSolid1,projectionLocationCASolid2,
        projectionLocationCASolid1,
            shaderProgramMain,
            shaderProgramSPSolid1,shaderProgramCASolid1,shaderProgramCASolid2,


            phongParts=0, projection = 1,nMaping=0,CASolid1,CASolid2,SPSolid1,reflector=0,
                    phongPartsCA1,phongPartsCA2,phongPartsSP1,
                    lightAmbCA1,lightAmbCA2,lightAmbSP1,
            lightPos = 1,surface,surfaceCA1=0,surfaceCA2=0,surfaceSP1=0,rMoveXY,
            scaleMSPSolid1,scaleMCASolid2,scaleMCASolid1,
            rotateMSPSolid1,rotateMCASolid2,rotateMCASolid1,

    reflectorCA1,reflectorCA1Angle,reflectorCA2,reflectorCA2Angle,
                    reflectorSP1,reflectorSP1Angle,
            eMapCA1,eMapCA2,eMapSP1;

    private Camera camera;
    private Mat4PerspRH persp;
    private Vec3D lightPosition, lightAmbient,Rmove;

    private  Mat4Scale scaleMat;
    private  Mat4RotXYZ rotateMat;

    private boolean mousePressed;
    private double oldMx, oldMy, ofst,oldMxR, oldMyR;

    int anim = 0, wire = 0, eMap = 0;
    float  rAngle=0.f;
    double scale = 1;

    private lwjglutils.OGLTextureCube textureSky,textureSky1;
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
        shaderProgramCASolid1 = ShaderUtils.loadProgram("/CASolid1");
        shaderProgramCASolid2 = ShaderUtils.loadProgram("/CASolid2");


        viewLocation = glGetUniformLocation(shaderProgramMain, "view");
        viewLocationSPSolid1= glGetUniformLocation(shaderProgramSPSolid1, "viewSPSolid1");
        viewLocationCASolid1= glGetUniformLocation(shaderProgramCASolid1, "viewCASolid1");
        viewLocationCASolid2= glGetUniformLocation(shaderProgramCASolid2, "viewCASolid2");


        viewPositionSPSolid1= glGetUniformLocation(shaderProgramSPSolid1, "viewPositionSPSolid1");
        viewPositionCASolid1= glGetUniformLocation(shaderProgramCASolid1, "viewPositionCASolid1");
        viewPositionCASolid2= glGetUniformLocation(shaderProgramCASolid2, "viewPositionCASolid2");

        lightLocation = glGetUniformLocation(shaderProgramMain, "light");
        lightLocationSPSolid1= glGetUniformLocation(shaderProgramSPSolid1, "lightSPSolid1");
        lightLocationCASolid1= glGetUniformLocation(shaderProgramCASolid1, "lightCASolid1");
        lightLocationCASolid2= glGetUniformLocation(shaderProgramCASolid2, "lightCASolid2");

        projectionLocation = glGetUniformLocation(shaderProgramMain, "projection");
        projectionLocationSPSolid1 = glGetUniformLocation(shaderProgramSPSolid1, "projectionSPSolid1");
        projectionLocationCASolid1 = glGetUniformLocation(shaderProgramCASolid1, "projectionCASolid1");
        projectionLocationCASolid2 = glGetUniformLocation(shaderProgramCASolid2, "projectionCASolid2");

        scaleMSPSolid1 = glGetUniformLocation(shaderProgramSPSolid1, "scaleMSPSolid1");
        scaleMCASolid1 = glGetUniformLocation(shaderProgramCASolid1, "scaleMCASolid1");
        scaleMCASolid2 = glGetUniformLocation(shaderProgramCASolid2, "scaleMCASolid2");

        rotateMSPSolid1 = glGetUniformLocation(shaderProgramSPSolid1, "rotateMSPSolid1");
        rotateMCASolid1 = glGetUniformLocation(shaderProgramCASolid1, "rotateMCASolid1");
        rotateMCASolid2 = glGetUniformLocation(shaderProgramCASolid2, "rotateMCASolid2");

        surfaceCA1 = glGetUniformLocation(shaderProgramCASolid1, "surfaceCA1");
        surfaceCA2 = glGetUniformLocation(shaderProgramCASolid2, "surfaceCA2");
        surfaceSP1 = glGetUniformLocation(shaderProgramSPSolid1, "surfaceSP1");

        lightAmbCA1 = glGetUniformLocation(shaderProgramCASolid1, "lightAmbCA1");
        lightAmbCA2 = glGetUniformLocation(shaderProgramCASolid2, "lightAmbCA2");
        lightAmbSP1 = glGetUniformLocation(shaderProgramSPSolid1, "lightAmbSP1");
;
        phongPartsCA1 = glGetUniformLocation(shaderProgramCASolid1, "phongPartsCA1");
        phongPartsCA2 = glGetUniformLocation(shaderProgramCASolid2, "phongPartsCA2");
        phongPartsSP1 = glGetUniformLocation(shaderProgramSPSolid1, "phongPartsP1");

        reflectorCA1 = glGetUniformLocation(shaderProgramCASolid1, "reflectorCA1");
        reflectorCA1Angle = glGetUniformLocation(shaderProgramCASolid1, "reflectorCA1Angle");
        reflectorCA2 = glGetUniformLocation(shaderProgramCASolid2, "reflectorCA2");
        reflectorCA2Angle = glGetUniformLocation(shaderProgramCASolid2, "reflectorCA2Angle");
        reflectorSP1 = glGetUniformLocation(shaderProgramSPSolid1, "reflectorSP1");
        reflectorSP1Angle = glGetUniformLocation(shaderProgramSPSolid1, "reflectorSP1Angle");

        eMapCA1 = glGetUniformLocation(shaderProgramCASolid1, "eMapCA1");
        eMapCA2 = glGetUniformLocation(shaderProgramCASolid2, "eMapCA2");
        eMapSP1 = glGetUniformLocation(shaderProgramSPSolid1, "eMapSP1");

        rMoveXY = glGetUniformLocation(shaderProgramCASolid1, "rMoveXY");

        camera = new Camera()
                //    .withPosition(new Vec3D(10, 10, 6))
             //   .withPosition(new Vec3D(25, 8., 5.))
                .withPosition(new Vec3D(0., 0., 1.))
          //      .withAzimuth(5.f * Math.PI)
                .withAzimuth(0.5f * Math.PI)
           //     .withAzimuth(0.45)
          //      .withZenith(-1 / 30f * Math.PI);
                .withZenith(-1/2f * Math.PI);
           //    .withZenith(0.45);

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
        buffersSky = p01simple.GridFactory.generateSkybox();
        buffersSPSolid1 = p01simple.GridFactory.generateGrid(100,100);
        buffersCASolid1 = p01simple.GridFactory.generateGrid(100,100);
        buffersCASolid2 = p01simple.GridFactory.generateGrid(100,100);

        try {
            textureSky = new lwjglutils.OGLTextureCube(new String[]{"./textures/yokohama/right.jpg","./textures/yokohama/left.jpg","./textures/yokohama/top.jpg","./textures/yokohama/bottom.jpg",
                    "./textures/yokohama/front.jpg","./textures/yokohama/back.jpg"});
            textureSky1 = new lwjglutils.OGLTextureCube(new String[]{"./textures/skyBox_right.jpg","./textures/skyBox_left.jpg","./textures/skyBox_top.jpg","./textures/skyBox_bottom.jpg",
                    "./textures/skyBox_front.jpg","./textures/skyBox_back.jpg"});
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

        if (lightPos == 1) {
            lightPosition = new Vec3D(0., 20., 10.);
        } else {
            lightPosition = new Vec3D(5, -10, 10);
        }

        scaleMat = new Mat4Scale(scale,scale,scale);
        rotateMat = new Mat4RotXYZ(ofst,ofst,ofst);
        Rmove = new Vec3D(oldMxR,oldMyR,0.f);

        //////////////////sharder Main
        glUseProgram(shaderProgramMain);
        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocation, false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocation, false,ortho.floatArray());}
        textureSky.bind(shaderProgramMain,"textureSky",0);
        glUniformMatrix4fv(viewLocation,false, camera.getViewMatrix().floatArray());
        glUniform3f (lightLocation,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
     //   buffers.draw(GL_TRIANGLES, shaderProgramMain);
        buffersSky.draw(GL_TRIANGLES, shaderProgramMain);

        //////////////////////// shader CASolid1
        glUseProgram(shaderProgramCASolid1);

        glUniform1i(phongPartsCA1,phongParts);
        glUniform1i(surfaceCA1,surface);
        glUniform1i(eMapCA1,eMap);
        glUniform1i(reflectorCA1,reflector);
        glUniform1f(reflectorCA1Angle,rAngle);
        textureSky.bind(shaderProgramCASolid1,"textureSky",0);
        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationCASolid1 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationCASolid1, false,ortho.floatArray());}

        glUniformMatrix4fv(viewLocationCASolid1,false, camera.getViewMatrix().floatArray());
        glUniform3f(viewPositionCASolid1, (float)camera.getPosition().getX(),(float)camera.getPosition().getY(),(float)camera.getPosition().getZ());
        glUniform3f(rMoveXY, (float)Rmove.getX(),(float)Rmove.getY(),(float)Rmove.getZ());
        glUniformMatrix4fv(scaleMCASolid1,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMCASolid1,false,rotateMat.floatArray());
        glUniform3f (lightLocationCASolid1,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbCA1,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());

        if (CASolid1 == 1){
            buffersCASolid1.draw(GL_TRIANGLES, shaderProgramCASolid1);
        }
        //////////////////////// shader CASolid2
        glUseProgram(shaderProgramCASolid2);

        glUniform1i(phongPartsCA2,phongParts);
        glUniform1i(surfaceCA2,surface);
        glUniform1i(reflectorCA2,reflector);
        glUniform1i(eMapCA2,eMap);
        glUniform1f(reflectorCA2Angle,rAngle);
        textureSky.bind(shaderProgramCASolid2,"textureSky",0);

        if(projection == 1 ){
            glUniformMatrix4fv(projectionLocationCASolid2 , false,persp.floatArray());}
        else if(projection == 2){
            glUniformMatrix4fv(projectionLocationCASolid2, false,ortho.floatArray());}

        glUniformMatrix4fv(viewLocationCASolid2,false, camera.getViewMatrix().floatArray());
        glUniform3f(viewPositionCASolid2, (float)camera.getPosition().getX(),(float)camera.getPosition().getY(),(float)camera.getPosition().getZ());
        glUniformMatrix4fv(scaleMCASolid2,false,scaleMat.floatArray());
        glUniformMatrix4fv(rotateMCASolid2,false,rotateMat.floatArray());
        glUniform3f (lightLocationCASolid2,(float) lightPosition.getX(),(float) lightPosition.getY(),(float) lightPosition.getZ());
        glUniform3f (lightAmbCA2,(float) lightAmbient.getX(),(float) lightAmbient.getY(),(float) lightAmbient.getZ());
        if (CASolid2 == 1){
            buffersCASolid2.draw(GL_TRIANGLES, shaderProgramCASolid2);
        }

        ///////////////////////////// shader SPSolid1
       glUseProgram(shaderProgramSPSolid1);

        glUniform1i(phongPartsSP1,phongParts);
        glUniform1i(surfaceSP1,surface);
        glUniform1i(reflectorSP1,reflector);
        glUniform1i(eMapSP1,eMap);
        glUniform1f(reflectorSP1Angle,rAngle);
        textureSky.bind(shaderProgramSPSolid1,"textureSky",1);

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

if (anim == 1) {

    ofst = Math.cos(TimeUnit.MILLISECONDS.toSeconds( (int) currentTimeMillis()*8));

}

    }
    private GLFWMouseButtonCallback mouseButtonCallback = new GLFWMouseButtonCallback () {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            if (button == 0) {
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
                  case GLFW_KEY_W:
                        camera = camera.forward(0.1);
                        break;
                    case GLFW_KEY_D:
                        camera = camera.right(0.1);
                        break;
                    case GLFW_KEY_S:
                        camera = camera.backward(0.1);
                        break;
                    case GLFW_KEY_A:
                        camera = camera.left(0.1);
                        break;
                    case GLFW_KEY_LEFT_CONTROL:
                        camera = camera.down(0.1);
                        break;
                    case GLFW_KEY_LEFT_SHIFT:
                        camera = camera.up(0.1);
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
                    case GLFW_KEY_G:
                            phongParts = 0;
                        break;
                    case GLFW_KEY_H:
                            phongParts = 1;
                    break;
                    case GLFW_KEY_J:
                            phongParts = 2;
                        break;
                    case GLFW_KEY_K:
                            phongParts = 3;
                        break;
                    case GLFW_KEY_P:
                        if (projection == 1){
                            projection = 2;}
                        else {projection = 1;}
                        break;
                    case GLFW_KEY_U:
                        if (reflector == 1){
                            reflector = 0;}
                        else {reflector  = 1;}
                        break;
                    case GLFW_KEY_I:
                        if (anim == 1){
                            anim = 0;}
                        else {anim  = 1;}
                        break;
                    case GLFW_KEY_O:
                        if (lightPos == 1){
                            lightPos = 0;}
                        else {lightPos  = 1;}
                        break;
                    case GLFW_KEY_Q:
                        if (nMaping == 1){
                            nMaping = 0;}
                        else {nMaping  = 1;}
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
                        if (SPSolid1 == 1){
                            SPSolid1 = 0;}
                        else {SPSolid1  = 1;}
                        break;
                    case GLFW_KEY_KP_SUBTRACT:
                       rAngle = rAngle - 1;
                        break;
                    case GLFW_KEY_KP_ADD:
                       rAngle = rAngle + 1;
                        break;
                    case GLFW_KEY_KP_MULTIPLY:
                        if (eMap == 1){
                            eMap  = 0;}
                        else {eMap   = 1;}
                        break;

                    case GLFW_KEY_KP_8:
                        oldMxR = oldMxR + 0.1f;
                        break;
                    case GLFW_KEY_KP_2:
                        oldMxR = oldMxR - 0.1f;
                        break;
                    case GLFW_KEY_KP_4:
                        oldMyR = oldMyR - 0.1f;
                        break;
                    case GLFW_KEY_KP_6:
                        oldMyR = oldMyR + 0.1f;
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




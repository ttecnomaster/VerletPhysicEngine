package com.github.tecnomaster0.demo;

public class Runner {
    private int fps;
    private volatile double ns;
    private final Runnable runnable;

    protected Runner(int fps, Runnable runnable) {
        this.setFps(fps);
        this.runnable = runnable;
    }

    public synchronized void setFps(int fps) {
        this.fps = fps;
        this.ns();
    }

    private void ns() {
        this.ns = 1.0E9 / (double)this.fps;
    }

    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(true) {
            if (this.fps != -1) {
                long now = System.nanoTime();
                delta += (double)(now - lastTime) / this.ns;

                for(lastTime = now; delta >= 1.0; --delta) {
                    this.tick();
                }

                ++frames;
                if (System.currentTimeMillis() - timer > 1000L) {
                    timer += 1000L;
                    frames = 0;
                }
            }
        }
    }

    public void tick() {
        this.runnable.run();
    }
}

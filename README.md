# Verlet Physic Engine

## How to use

**Verlet Objects can be instantiated by using the static methods from the Verlet class**

- Create a Scene Object
```java
Scene scene = Verlet.createScene();
```
- Add Constraint Objects to your Scene
```java
// (xPos, yPos, constraintRadius)
Constraint circleConstraint = new CircleAreaConstraint(0, 0, 200);
scene.addConstraint(circleConstraint);
```
- Add Sphere Objects to your Scene
```java
// (xPos, yPos, sphereRadius)
Sphere sphere = Verlet.createSphere(-75, 25, 10);
scene.addSphere(sphere);
```
- Create a Solver in order to run the Simulation
```java
Solver solver = Verlet.createSolver(scene);
```
- Instantiate a JPanel which will render your Scene
```java
VerletPanel panel = new VerletPanel(scene);

// Assuming there is a JFrame present
frame.add(panel);
```
- Setup runtime
```java
public void tick() {
  // Steps the entire simulation. Parameter decides how far it steps
  solver.step(0.02f);

  // Renders the new Scene onto the panel
  panel.repaint();
}
```

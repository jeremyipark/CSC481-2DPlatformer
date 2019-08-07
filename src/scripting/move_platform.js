function update() {
	upper_bound = 150;
	lower_bound = 500;
	
    if (obj.y < upper_bound) {
        moveComponent.ySpeed *= -1;
        obj.y = upper_bound;
    } else if (obj.y > lower_bound) {
    	moveComponent.ySpeed *= -1;
        obj.y = lower_bound;
    }

    obj.y += moveComponent.ySpeed;
}

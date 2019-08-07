function update() {
	// spawn in provided spawn point
	spawn_x = spawn.getSpawnPoint().getX();
	spawn_y = spawn.getSpawnPoint().getY();
	
	// always spawn in top left
	spawn_x = 0;
	spawn_y = 0;

    if (spawn.getGUID() == box.getGUID()) {
        box.setLocation( spawn_x, spawn_y );
    }
}

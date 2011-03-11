package com.westmacott.tom.snakes.engine;

import com.westmacott.tom.snakes.Location;

public class Level {
	
	public final String name;
	public final String data;
	public final int id;
	public final Location startLocation;
	
	public Level(int id, String name, String data) {
		this(id, name, data, new Location(10, 10));
	}

	public Level(int id, String name, String data, Location location) {
		this.id = id;
		this.name = name;
		this.data = data;
		this.startLocation = location;
	}

	public static final Level[] all = {
		new Level(1, "Square One", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/1,2/30,3/1,3/30,4/1,4/30,5/1,5/30,6/1,6/30,7/1,7/30,8/1,8/30,9/1,9/30,10/1,10/30,11/1,11/30,12/1,12/30,13/1,13/30,14/1,14/30,15/1,15/30,16/1,16/30,17/1,17/30,18/1,18/30,19/1,19/30,20/1,20/30,21/1,21/30,22/1,22/30,23/1,23/30,24/1,24/30,25/1,25/30,26/1,26/30,27/1,27/30,28/1,28/30,29/1,29/30,30/1,30/30,31/1,31/30,32/1,32/30,33/1,33/30,34/1,34/30,35/1,35/30,36/1,36/30,37/1,37/30,38/1,38/30,39/1,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/27,40/28,40/29,40/30,"
		),
		new Level(2, "The Ladder", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/1,2/30,3/1,3/30,4/1,4/30,5/1,5/30,6/1,6/30,7/1,7/30,8/1,8/30,9/1,9/30,10/1,10/30,11/1,11/30,12/1,12/19,12/20,12/21,12/22,12/23,12/24,12/25,12/26,12/27,12/28,12/29,12/30,13/1,13/30,14/1,14/30,15/1,15/30,16/1,16/30,17/1,17/2,17/3,17/4,17/5,17/6,17/7,17/8,17/9,17/10,17/11,17/12,17/30,18/1,18/30,19/1,19/30,20/1,20/30,21/1,21/30,22/1,22/30,23/1,23/30,24/1,24/19,24/20,24/21,24/22,24/23,24/24,24/25,24/26,24/27,24/28,24/29,24/30,25/1,25/30,26/1,26/30,27/1,27/30,28/1,28/30,29/1,29/2,29/3,29/4,29/5,29/6,29/7,29/8,29/9,29/10,29/11,29/12,29/30,30/1,30/30,31/1,31/30,32/1,32/30,33/1,33/30,34/1,34/30,35/1,35/30,36/1,36/19,36/20,36/21,36/22,36/23,36/24,36/25,36/26,36/27,36/28,36/29,36/30,37/1,37/30,38/1,38/30,39/1,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/27,40/28,40/29,40/30,"
		),
		new Level(4, "Medicine Bottle", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/1,2/30,3/1,3/30,4/1,4/30,5/1,5/30,6/1,6/6,6/25,6/30,7/1,7/6,7/25,7/30,8/1,8/6,8/13,8/14,8/15,8/16,8/17,8/18,8/19,8/25,8/30,9/1,9/6,9/13,9/19,9/25,9/30,10/1,10/6,10/13,10/19,10/25,10/30,11/1,11/6,11/13,11/19,11/25,11/30,12/1,12/6,12/13,12/19,12/25,12/30,13/1,13/6,13/13,13/19,13/25,13/30,14/1,14/6,14/13,14/19,14/25,14/30,15/1,15/6,15/25,15/30,16/1,16/6,16/25,16/30,17/1,17/2,17/3,17/4,17/5,17/6,17/25,17/26,17/27,17/28,17/29,17/30,18/1,18/14,18/18,18/30,19/1,19/14,19/18,19/30,20/1,20/14,20/18,20/30,21/1,21/14,21/18,21/30,22/1,22/14,22/18,22/30,23/1,23/14,23/18,23/30,24/1,24/4,24/5,24/7,24/8,24/9,24/10,24/11,24/12,24/13,24/14,24/18,24/19,24/20,24/21,24/22,24/23,24/24,24/26,24/27,24/30,25/1,25/4,25/5,25/7,25/24,25/26,25/27,25/30,26/1,26/7,26/24,26/30,27/1,27/7,27/24,27/30,28/1,28/7,28/24,28/30,29/1,29/4,29/5,29/7,29/24,29/26,29/27,29/30,30/1,30/4,30/5,30/7,30/24,30/26,30/27,30/30,31/1,31/7,31/24,31/30,32/1,32/7,32/24,32/30,33/1,33/7,33/24,33/30,34/1,34/4,34/5,34/7,34/24,34/26,34/27,34/30,35/1,35/4,35/5,35/7,35/8,35/9,35/10,35/11,35/12,35/13,35/14,35/15,35/16,35/17,35/18,35/19,35/20,35/21,35/22,35/23,35/24,35/26,35/27,35/30,36/1,36/30,37/1,37/30,38/1,38/30,39/1,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/27,40/28,40/29,40/30,"
		)
		,
		new Level(5, "Stairway to Hell", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/1,2/19,2/22,2/25,2/28,2/29,2/30,3/1,3/19,3/22,3/25,3/28,3/29,3/30,4/1,4/4,4/5,4/13,4/14,4/19,4/30,5/1,5/4,5/5,5/13,5/14,5/19,5/30,6/1,6/19,6/28,6/29,6/30,7/1,7/30,8/1,8/19,8/30,9/1,9/12,9/19,9/28,9/29,9/30,10/1,10/12,10/19,10/30,11/1,11/12,11/19,11/30,12/1,12/12,12/13,12/19,12/28,12/29,12/30,13/1,13/12,13/13,13/14,13/19,13/30,14/1,14/14,14/15,14/19,14/30,15/1,15/15,15/16,15/19,15/30,16/1,16/16,16/17,16/18,16/19,16/30,17/1,17/12,17/13,17/17,17/18,17/19,17/22,17/30,18/1,18/12,18/13,18/14,18/18,18/19,18/22,18/30,19/1,19/7,19/8,19/9,19/10,19/11,19/12,19/13,19/14,19/15,19/19,19/20,19/21,19/22,19/30,20/1,20/15,20/16,20/20,20/21,20/22,20/25,20/30,21/1,21/7,21/8,21/9,21/10,21/11,21/12,21/13,21/15,21/16,21/17,21/21,21/22,21/25,21/30,22/1,22/13,22/15,22/16,22/17,22/18,22/22,22/23,22/24,22/25,22/30,23/1,23/9,23/10,23/11,23/13,23/15,23/18,23/19,23/23,23/24,23/25,23/26,23/28,23/29,23/30,24/1,24/13,24/15,24/19,24/20,24/30,25/1,25/9,25/13,25/15,25/20,25/21,25/30,26/1,26/9,26/13,26/15,26/21,26/22,26/30,27/1,27/9,27/13,27/15,27/22,27/23,27/24,27/25,27/30,28/1,28/30,29/1,29/9,29/30,30/1,30/9,30/30,31/1,31/9,31/30,32/1,32/6,32/19,32/30,33/1,33/6,33/9,33/18,33/19,33/20,33/30,34/1,34/6,34/9,34/19,34/25,34/30,35/1,35/6,35/9,35/24,35/25,35/26,35/30,36/1,36/6,36/13,36/25,36/30,37/1,37/6,37/12,37/13,37/14,37/30,38/1,38/6,38/13,38/30,39/1,39/6,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/27,40/28,40/29,40/30,"
		)
		,
		new Level(3, "Hamster Cage", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/1,2/30,3/1,3/30,4/1,4/5,4/6,4/7,4/8,4/13,4/14,4/15,4/16,4/21,4/22,4/23,4/24,4/25,4/30,5/1,5/30,6/1,6/30,7/1,7/2,7/3,7/4,7/9,7/10,7/11,7/12,7/17,7/18,7/19,7/20,7/26,7/27,7/28,7/29,7/30,8/1,8/30,9/1,9/30,10/1,10/30,11/1,11/30,12/1,12/3,12/4,12/5,12/6,12/7,12/8,12/12,12/13,12/14,12/15,12/16,12/17,12/18,12/19,12/20,12/21,12/22,12/23,12/24,12/25,12/30,13/1,13/3,13/8,13/12,13/19,13/25,13/26,13/27,13/30,14/1,14/3,14/8,14/12,14/19,14/25,14/30,15/1,15/3,15/8,15/12,15/19,15/25,15/30,16/1,16/3,16/8,16/12,16/19,16/25,16/30,17/1,17/3,17/8,17/12,17/19,17/25,17/28,17/29,17/30,18/1,18/3,18/8,18/12,18/19,18/22,18/25,18/30,19/1,19/3,19/6,19/7,19/8,19/12,19/13,19/14,19/15,19/19,19/22,19/25,19/30,20/1,20/3,20/19,20/22,20/25,20/26,20/27,20/30,21/1,21/3,21/18,21/19,21/22,21/25,21/30,22/1,22/3,22/19,22/25,22/30,23/1,23/3,23/6,23/7,23/8,23/9,23/10,23/11,23/12,23/13,23/14,23/15,23/19,23/25,23/28,23/29,23/30,24/1,24/3,24/8,24/19,24/25,24/30,25/1,25/3,25/8,25/19,25/22,25/25,25/30,26/1,26/3,26/4,26/5,26/8,26/19,26/22,26/25,26/26,26/27,26/30,27/1,27/3,27/8,27/12,27/13,27/14,27/15,27/19,27/22,27/25,27/30,28/1,28/3,28/8,28/12,28/16,28/17,28/18,28/19,28/22,28/25,28/30,29/1,29/3,29/6,29/7,29/8,29/19,29/25,29/28,29/29,29/30,30/1,30/3,30/8,30/19,30/25,30/30,31/1,31/3,31/8,31/19,31/25,31/30,32/1,32/3,32/4,32/5,32/8,32/9,32/10,32/11,32/12,32/16,32/17,32/18,32/19,32/22,32/23,32/24,32/25,32/26,32/27,32/28,32/30,33/1,33/3,33/8,33/19,33/22,33/28,33/30,34/1,34/3,34/19,34/22,34/28,34/30,35/1,35/3,35/8,35/19,35/22,35/28,35/30,36/1,36/3,36/4,36/6,36/7,36/8,36/9,36/10,36/11,36/12,36/13,36/14,36/15,36/16,36/17,36/18,36/19,36/22,36/28,36/30,37/1,37/19,37/22,37/25,37/26,37/27,37/28,37/30,38/1,38/30,39/1,39/19,39/22,39/25,39/26,39/27,39/28,39/29,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/27,40/28,40/29,40/30,"
				, new Location(9, 15)
		)
		,
		new Level(6, "Crossroads", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/1,2/25,2/26,2/27,2/28,2/29,2/30,3/1,3/22,3/26,3/27,3/28,3/29,3/30,4/1,4/5,4/14,4/22,4/23,4/27,4/28,4/29,4/30,5/1,5/4,5/5,5/6,5/13,5/14,5/15,5/23,5/24,5/28,5/29,5/30,6/1,6/5,6/14,6/24,6/25,6/29,6/30,7/1,7/25,7/26,7/29,7/30,8/1,8/25,8/30,9/1,9/25,9/30,10/1,10/25,10/30,11/1,11/12,11/13,11/14,11/15,11/16,11/17,11/18,11/19,11/20,11/21,11/22,11/23,11/24,11/25,11/30,12/1,12/5,12/12,12/21,12/30,13/1,13/4,13/5,13/6,13/12,13/21,13/30,14/1,14/5,14/12,14/21,14/26,14/27,14/28,14/29,14/30,15/1,15/12,15/21,15/30,16/1,16/12,16/21,16/30,17/1,17/12,17/16,17/17,17/21,17/30,18/1,18/2,18/3,18/4,18/12,18/16,18/17,18/21,18/22,18/23,18/24,18/25,18/30,19/1,19/4,19/12,19/16,19/17,19/21,19/30,20/1,20/4,20/12,20/16,20/17,20/21,20/30,21/1,21/4,21/12,21/16,21/17,21/21,21/30,22/1,22/4,22/8,22/9,22/10,22/11,22/12,22/13,22/15,22/16,22/17,22/21,22/26,22/27,22/28,22/29,22/30,23/1,23/4,23/13,23/15,23/21,23/30,24/1,24/4,24/21,24/30,25/1,25/4,25/13,25/15,25/21,25/30,26/1,26/4,26/5,26/6,26/7,26/8,26/9,26/10,26/11,26/12,26/13,26/15,26/16,26/17,26/18,26/19,26/20,26/21,26/22,26/23,26/24,26/25,26/30,27/1,27/13,27/15,27/30,28/1,28/30,29/1,29/30,30/1,30/26,30/27,30/28,30/29,30/30,31/1,31/4,31/5,31/6,31/7,31/8,31/9,31/10,31/11,31/12,31/13,31/30,32/1,32/4,32/30,33/1,33/4,33/30,34/1,34/4,34/30,35/1,35/4,35/8,35/9,35/10,35/20,35/21,35/22,35/30,36/1,36/4,36/14,36/15,36/16,36/26,36/27,36/28,36/29,36/30,37/1,37/4,37/30,38/1,38/4,38/30,39/1,39/4,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/27,40/28,40/29,40/30,"
		)
		,
		new Level(7, "Key to the Door", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/30,2/1,2/2,2/3,2/4,2/5,2/6,2/7,2/8,2/9,2/10,2/11,2/30,3/1,3/2,3/3,3/4,3/5,3/6,3/7,3/8,3/30,4/1,4/2,4/3,4/4,4/5,4/6,4/14,4/15,4/25,4/26,4/30,5/1,5/2,5/3,5/4,5/5,5/13,5/14,5/15,5/16,5/24,5/25,5/26,5/27,5/30,6/1,6/2,6/3,6/4,6/13,6/14,6/15,6/16,6/24,6/25,6/26,6/27,6/30,7/1,7/2,7/3,7/14,7/15,7/25,7/26,7/30,8/1,8/2,8/3,8/30,9/1,9/2,9/30,10/1,10/2,10/18,10/19,10/20,10/21,10/30,11/1,11/2,11/14,11/15,11/16,11/19,11/20,11/21,11/30,12/1,12/5,12/6,12/7,12/13,12/14,12/15,12/16,12/17,12/20,12/21,12/30,13/1,13/5,13/6,13/7,13/12,13/13,13/14,13/17,13/18,13/21,13/22,13/23,13/24,13/25,13/26,13/30,14/1,14/5,14/6,14/7,14/12,14/13,14/18,14/19,14/26,14/30,15/1,15/5,15/6,15/7,15/11,15/12,15/19,15/26,15/30,16/1,16/5,16/6,16/7,16/11,16/12,16/19,16/26,16/30,17/1,17/5,17/6,17/7,17/11,17/12,17/19,17/26,17/30,18/19,18/20,18/21,18/22,18/23,18/24,18/26,19/24,19/26,20/19,20/20,20/21,20/22,20/24,20/26,21/1,21/5,21/6,21/7,21/11,21/12,21/19,21/22,21/24,21/26,21/30,22/1,22/5,22/6,22/7,22/11,22/12,22/19,22/22,22/24,22/26,22/30,23/1,23/5,23/6,23/7,23/11,23/12,23/19,23/22,23/24,23/26,23/30,24/1,24/5,24/6,24/7,24/12,24/13,24/18,24/19,24/30,25/1,25/5,25/6,25/7,25/12,25/13,25/18,25/19,25/30,26/1,26/5,26/6,26/7,26/13,26/14,26/17,26/18,26/30,27/1,27/14,27/15,27/16,27/17,27/27,27/28,27/29,27/30,28/1,28/14,28/15,28/16,28/17,28/30,29/1,29/30,30/1,30/30,31/1,31/27,31/28,31/29,31/30,32/1,32/5,32/6,32/12,32/13,32/19,32/20,32/30,33/1,33/4,33/5,33/6,33/7,33/11,33/12,33/13,33/14,33/18,33/19,33/20,33/21,33/30,34/1,34/4,34/5,34/6,34/7,34/11,34/12,34/13,34/14,34/18,34/19,34/20,34/21,34/30,35/1,35/5,35/6,35/12,35/13,35/19,35/20,35/27,35/28,35/29,35/30,38/1,38/30,39/1,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/30,"
				, new Location(8, 10)
		)
		,
		new Level(8, "Wiggle Wiggle", 
				"1/1,1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/1,2/22,2/30,3/1,3/22,3/30,4/1,4/22,4/30,5/1,5/4,5/5,5/6,5/7,5/8,5/9,5/10,5/11,5/12,5/13,5/17,5/19,5/22,5/25,5/26,5/27,5/30,6/1,6/4,6/13,6/17,6/19,6/22,6/25,6/30,7/1,7/4,7/13,7/17,7/19,7/22,7/25,7/30,8/1,8/4,8/13,8/17,8/19,8/22,8/25,8/26,8/27,8/30,9/1,9/4,9/13,9/17,9/19,9/22,9/27,9/30,10/1,10/4,10/13,10/17,10/19,10/22,10/27,10/30,11/1,11/4,11/13,11/17,11/19,11/22,11/25,11/26,11/27,11/30,12/1,12/4,12/13,12/17,12/19,12/22,12/25,12/30,13/1,13/4,13/5,13/6,13/7,13/10,13/11,13/12,13/13,13/17,13/19,13/22,13/25,13/30,14/1,14/7,14/10,14/22,14/25,14/26,14/27,14/30,15/1,15/7,15/10,15/22,15/27,15/30,16/22,17/22,18/22,18/25,19/1,19/2,19/3,19/4,19/5,19/6,19/7,19/8,19/9,19/10,19/11,19/12,19/13,19/14,19/15,19/16,19/17,19/18,19/19,19/20,19/21,19/22,19/25,19/26,19/27,19/28,19/29,19/30,20/22,20/25,20/27,21/22,21/27,22/22,22/27,23/1,23/5,23/6,23/7,23/8,23/9,23/10,23/11,23/17,23/18,23/22,23/27,23/30,24/1,24/11,24/16,24/17,24/18,24/19,24/22,24/23,24/24,24/27,24/30,25/1,25/11,25/16,25/17,25/18,25/19,25/22,25/27,25/30,26/1,26/11,26/17,26/18,26/22,26/27,26/30,27/1,27/11,27/22,27/27,27/30,28/1,28/11,28/22,28/25,28/26,28/27,28/30,29/1,29/11,29/22,29/25,29/30,30/1,30/11,30/22,30/25,30/30,31/1,31/2,31/3,31/4,31/5,31/6,31/7,31/8,31/9,31/10,31/11,31/16,31/17,31/18,31/19,31/20,31/21,31/22,31/25,31/30,32/1,32/8,32/22,32/25,32/28,32/29,32/30,33/1,33/8,33/16,33/17,33/18,33/19,33/20,33/22,33/25,33/30,34/1,34/8,34/20,34/22,34/25,34/30,35/1,35/4,35/5,35/8,35/20,35/22,35/25,35/30,36/1,36/4,36/5,36/8,36/20,36/22,36/25,36/26,36/27,36/30,37/1,37/20,37/22,37/30,38/1,38/20,38/22,38/30,39/1,39/20,39/22,39/30,40/1,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/22,40/23,40/24,40/25,40/26,40/27,40/28,40/29,40/30,"
				, new Location(14, 10)
		)
		,
		new Level(9, "Maze",
				"1/2,1/3,1/4,1/5,1/6,1/7,1/8,1/9,1/10,1/11,1/12,1/13,1/14,1/15,1/16,1/17,1/18,1/19,1/20,1/21,1/22,1/23,1/24,1/25,1/26,1/27,1/28,1/29,1/30,2/7,2/21,2/30,3/1,3/7,3/21,3/30,4/1,4/7,4/21,4/30,5/1,5/8,5/21,5/30,6/1,6/9,6/21,6/30,7/1,7/2,7/3,7/4,7/5,7/6,7/10,7/21,7/22,7/23,7/24,7/27,7/28,7/29,7/30,8/1,8/7,8/11,8/24,8/27,8/30,9/1,9/8,9/12,9/24,9/27,9/30,10/1,10/9,10/13,10/18,10/30,11/1,11/10,11/14,11/15,11/16,11/17,11/18,11/30,12/1,12/4,12/7,12/11,12/27,12/30,13/1,13/4,13/7,13/12,13/27,13/30,14/1,14/4,14/7,14/12,14/13,14/14,14/15,14/16,14/17,14/18,14/27,14/30,15/1,15/4,15/5,15/6,15/7,15/12,15/18,15/24,15/25,15/26,15/27,15/30,16/1,16/4,16/7,16/12,16/30,17/1,17/4,17/7,17/12,17/30,18/1,18/4,18/7,18/12,18/15,18/16,18/17,18/18,18/19,18/20,18/21,18/22,18/23,18/24,18/25,18/26,18/27,18/28,18/29,18/30,19/1,19/12,19/23,19/30,20/1,20/12,20/23,20/30,21/1,21/12,21/23,21/30,22/1,22/19,22/27,22/30,23/1,23/6,23/19,23/27,23/30,24/1,24/2,24/6,24/10,24/11,24/19,24/27,24/30,25/1,25/2,25/6,25/9,25/10,25/11,25/12,25/15,25/16,25/17,25/18,25/19,25/20,25/21,25/22,25/23,25/24,25/25,25/26,25/27,25/30,26/1,26/2,26/6,26/9,26/10,26/11,26/12,26/15,26/16,26/23,26/27,26/30,27/1,27/2,27/3,27/7,27/10,27/11,27/15,27/16,27/23,27/27,27/30,28/1,28/2,28/3,28/7,28/15,28/16,28/19,28/23,28/27,28/30,29/1,29/2,29/3,29/7,29/15,29/16,29/19,29/23,29/27,29/30,30/1,30/4,30/8,30/15,30/16,30/19,30/23,30/27,30/30,31/1,31/4,31/9,31/15,31/16,31/19,31/23,31/27,31/30,32/1,32/5,32/10,32/11,32/12,32/15,32/16,32/19,32/23,32/27,32/30,33/1,33/6,33/13,33/14,33/15,33/16,33/19,33/27,33/30,34/1,34/7,34/15,34/16,34/19,34/27,34/30,35/1,35/8,35/9,35/19,35/27,35/30,36/1,36/10,36/11,36/12,36/19,36/20,36/21,36/22,36/23,36/24,36/25,36/26,36/27,36/30,37/1,37/13,37/14,37/15,37/16,37/30,38/1,38/30,39/1,39/2,39/30,40/2,40/3,40/4,40/5,40/6,40/7,40/8,40/9,40/10,40/11,40/12,40/13,40/14,40/15,40/16,40/17,40/18,40/19,40/20,40/21,40/22,40/23,40/24,40/25,40/26,40/27,40/28,"
				, new Location(24, 31)
		)
		,
		new Level(10, "Cross-Eyed",
				"1/8,1/15,1/16,1/27,2/4,2/12,2/15,2/16,3/4,3/12,3/15,3/16,3/21,4/4,4/12,4/15,4/16,4/17,4/21,4/25,4/26,5/1,5/2,5/3,5/4,5/5,5/6,5/7,5/8,5/9,5/10,5/11,5/12,5/15,5/16,5/17,5/21,5/24,5/25,5/26,5/27,5/30,6/1,6/8,6/12,6/15,6/16,6/17,6/21,6/24,6/25,6/26,6/27,6/30,7/1,7/8,7/12,7/15,7/16,7/17,7/18,7/22,7/25,7/26,7/30,8/1,8/4,8/8,8/12,8/15,8/16,8/17,8/18,8/22,8/30,9/1,9/4,9/8,9/12,9/15,9/16,9/17,9/18,9/22,9/30,10/1,10/4,10/8,10/12,10/15,10/16,10/19,10/23,10/30,11/1,11/4,11/8,11/12,11/15,11/16,11/19,11/24,11/30,12/1,12/4,12/8,12/12,12/15,12/16,12/20,12/25,12/26,12/27,12/30,13/1,13/4,13/12,13/15,13/16,13/21,13/28,13/29,13/30,14/1,14/4,14/12,14/15,14/16,14/22,14/30,15/4,15/12,15/15,15/16,15/23,15/24,16/4,16/5,16/6,16/7,16/8,16/9,16/10,16/11,16/12,16/15,16/16,16/25,16/26,16/27,17/1,17/15,17/16,17/28,17/29,17/30,18/15,18/16,19/15,19/16,19/17,20/1,20/2,20/3,20/4,20/5,20/6,20/7,20/8,20/9,20/10,20/11,20/12,20/13,20/17,20/18,20/19,20/20,20/21,20/22,20/23,20/24,20/25,20/26,20/27,20/28,20/29,20/30,21/1,21/2,21/3,21/4,21/5,21/6,21/7,21/8,21/9,21/10,21/11,21/12,21/13,21/14,21/15,21/17,21/18,21/19,21/20,21/21,21/22,21/23,21/24,21/25,21/26,21/27,21/28,21/29,21/30,22/6,22/15,22/22,23/6,23/15,23/16,23/22,24/6,24/15,24/16,24/22,25/6,25/15,25/16,25/23,26/6,26/15,26/16,26/24,27/6,27/7,27/8,27/9,27/12,27/13,27/14,27/15,27/16,27/17,27/18,27/19,27/20,27/21,27/25,28/9,28/12,28/15,28/16,28/22,28/26,29/9,29/12,29/15,29/16,29/23,29/27,30/3,30/15,30/16,30/24,30/28,31/1,31/2,31/3,31/15,31/16,31/25,31/29,31/30,32/12,32/15,32/16,32/19,32/22,32/26,33/12,33/15,33/16,33/19,33/22,33/27,34/1,34/2,34/3,34/12,34/15,34/16,34/19,34/22,34/27,34/28,34/29,34/30,35/3,35/9,35/10,35/11,35/12,35/15,35/16,35/19,35/20,35/21,35/22,35/27,36/15,36/16,36/19,36/22,36/27,37/15,37/16,37/19,37/22,37/27,38/1,38/2,38/3,38/4,38/5,38/6,38/7,38/8,38/9,38/10,38/11,38/12,38/13,38/14,38/15,38/16,38/19,38/22,38/27,38/30,39/8,39/15,39/16,39/27,40/8,40/15,40/16,40/27,"
		)
	};
}
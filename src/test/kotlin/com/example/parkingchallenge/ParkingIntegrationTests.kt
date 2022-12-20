package com.example.parkingchallenge

import com.example.parkingchallenge.exceptions.NoAvailableSpot
import com.example.parkingchallenge.model.Car
import com.example.parkingchallenge.model.ParkingAreaControl
import com.example.parkingchallenge.model.Size
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@Suppress("ClassName")
class ParkingIntegrationTests {

	@Nested
	inner class `Should be able to allocate small cars when`{

		@Test
		fun `there is spots with the same size available`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.SMALL))

			parkingAreaControl.hasSpotAvailableFor(Size.SMALL) `should be` false
		}

		@Test
		fun `there is no spots with the same size but only medium available`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.SMALL))
			parkingAreaControl.allocate(Car(Size.SMALL))

			parkingAreaControl.hasSpotAvailableFor(Size.MEDIUM) `should be` false
		}

		@Test
		fun `there is no spots small nor medium but the parking has still large spots available`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.SMALL))
			parkingAreaControl.allocate(Car(Size.SMALL))

			parkingAreaControl.hasSpotAvailableFor(Size.MEDIUM) `should be` false
		}

	}

	@Nested
	inner class `Should be able to allocate medium cars when`{

		@Test
		fun `there is spots with the same size available`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.MEDIUM))

			parkingAreaControl.hasSpotAvailableFor(Size.MEDIUM) `should be` false
		}

		@Test
		fun `there is no spots with the same size but only large available`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.MEDIUM))
			parkingAreaControl.allocate(Car(Size.MEDIUM))

			parkingAreaControl.hasSpotAvailableFor(Size.LARGE) `should be` false
		}

	}

	@Nested
	inner class `Should be able to allocate large cars when`{

		@Test
		fun `there is spots with the same size available`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.LARGE))

			parkingAreaControl.hasSpotAvailableFor(Size.LARGE) `should be` false
		}
	}

	@Nested
	inner class `Should not`{
		
		@Test
		fun `be able to allocate there is no spots available for the car's size`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.MEDIUM))
			parkingAreaControl.allocate(Car(Size.MEDIUM))

			invoking { parkingAreaControl.allocate(Car(Size.MEDIUM)) } `should throw` NoAvailableSpot::class
		}

		@Test
		fun `allocate medium cars in small spots`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.MEDIUM))

			parkingAreaControl.hasSpotAvailableFor(Size.SMALL) `should be` true
		}

		@Test
		fun `allocate large cars in medium spots`() {
			val parkingAreaControl = ParkingAreaControl()
			parkingAreaControl.allocate(Car(Size.LARGE))

			parkingAreaControl.hasSpotAvailableFor(Size.MEDIUM) `should be` true
			parkingAreaControl.hasSpotAvailableFor(Size.MEDIUM) `should be` true
		}
	}
}

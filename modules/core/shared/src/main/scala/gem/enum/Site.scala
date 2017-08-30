// Copyright (c) 2016-2017 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package gem
package enum

import cats.syntax.eq._
import cats.instances.string._
import gem.util.Enumerated

/**
 * Enumerated type for Gemini observing sites.
 * @group Enumerations (Generated)
 */
sealed abstract class Site(
  val tag: String,
  val shortName: String,
  val longName: String,
  val timezone: java.time.ZoneId
)

object Site {

  /** @group Constructors */ case object GN extends Site("GN", "GN", "Gemini North", java.time.ZoneId.of("Pacific/Honolulu"))
  /** @group Constructors */ case object GS extends Site("GS", "GS", "Gemini South", java.time.ZoneId.of("America/Santiago"))

  /** All members of Site, in canonical order. */
  val all: List[Site] =
    List(GN, GS)

  /** Select the member of Site with the given tag, if any. */
  def fromTag(s: String): Option[Site] =
    all.find(_.tag === s)

  /** Select the member of Site with the given tag, throwing if absent. */
  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def unsafeFromTag(s: String): Site =
    fromTag(s).getOrElse(throw new NoSuchElementException(s))

  /** @group Typeclass Instances */
  implicit val SiteEnumerated: Enumerated[Site] =
    new Enumerated[Site] {
      def all = Site.all
      def tag(a: Site) = a.tag
    }

}
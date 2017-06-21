// Copyright (c) 2016-2017 Association of Universities for Research in Astronomy, Inc. (AURA)
// For license information see LICENSE or https://opensource.org/licenses/BSD-3-Clause

package gem.ocs2

import gem.config._
import gem.enum._
import gem.ocs2.pio.{PioDecoder, PioError}
import gem.ocs2.pio.PioError.missingKey

import scala.xml.Node

import scalaz._
import Scalaz._

/** Decoder for the OCS2 static configuration XML.
  */
object StaticDecoder extends PioDecoder[StaticConfig] {

  private def parseStaticConfig(i: Instrument, cm: ConfigMap): PioError \/ StaticConfig =
    i match {
      case Instrument.AcqCam     => AcqCamStaticConfig().right
      case Instrument.Bhros      => BhrosStaticConfig().right

      case Instrument.Flamingos2 =>
        Legacy.Instrument.MosPreImaging.parse(cm).map(F2StaticConfig(_))

      case Instrument.GmosN      => GmosNorthStaticConfig.Default.right
      case Instrument.GmosS      => GmosSouthStaticConfig.Default.right
      case Instrument.Gnirs      => GnirsStaticConfig()          .right
      case Instrument.Gpi        => GpiStaticConfig()            .right
      case Instrument.Gsaoi      => GsaoiStaticConfig()          .right
      case Instrument.Michelle   => MichelleStaticConfig()       .right
      case Instrument.Nici       => NiciStaticConfig()           .right
      case Instrument.Nifs       => NifsStaticConfig()           .right
      case Instrument.Niri       => NiriStaticConfig()           .right
      case Instrument.Phoenix    => PhoenixStaticConfig()        .right
      case Instrument.Trecs      => TrecsStaticConfig()          .right
      case Instrument.Visitor    => VisitorStaticConfig()        .right
    }

  def decode(n: Node): PioError \/ StaticConfig =
    for {
      cm <- ((n \ "step").headOption \/> missingKey("step")).map(_.toStepConfig)
      i  <- Legacy.Instrument.Instrument.parse(cm)
      sc <- parseStaticConfig(i, cm)
    } yield sc
}

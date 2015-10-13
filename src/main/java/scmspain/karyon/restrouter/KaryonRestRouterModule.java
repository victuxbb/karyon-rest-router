package scmspain.karyon.restrouter;

import io.netty.buffer.ByteBuf;
import netflix.karyon.transport.http.KaryonHttpModule;
import scmspain.karyon.restrouter.handlers.ErrorHandler;
import scmspain.karyon.restrouter.serializer.Configuration;
import scmspain.karyon.restrouter.serializer.Serializer;
import scmspain.karyon.restrouter.serializer.SerializeManager;
import scmspain.karyon.restrouter.transport.http.RestUriRouter;

public abstract class KaryonRestRouterModule extends KaryonHttpModule<ByteBuf, ByteBuf> {

  private Configuration configuration;

  public KaryonRestRouterModule() {
    super("karyonRestModule", ByteBuf.class, ByteBuf.class);
  }

  protected KaryonRestRouterModule(String moduleName) {
    super(moduleName, ByteBuf.class, ByteBuf.class);
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  protected void configure() {
    bind(RestUriRouter.class);
    bind(RestRouterScanner.class);

    super.configure();

    bind(SerializeManager.class).toInstance(new SerializeManager(configuration));
    bindRouter().to(RestRouterHandler.class);
  }

}

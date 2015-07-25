package org.embulk.tester;

import java.util.ArrayList;
import java.util.List;

import org.embulk.config.ConfigSource;
import org.embulk.plugin.InjectedPluginSource;
import org.embulk.spi.Extension;

import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.Module;


public class TestExtension implements Extension
{
    private static class PluginDefinition
    {
        public final Class<?> iface;
        public final String name;
        public final Class<?> impl;

        public PluginDefinition(Class<?> iface, String name, Class<?> impl)
        {
            this.iface = iface;
            this.name = name;
            this.impl = impl;
        }
    }

    private static List<PluginDefinition> plugins = new ArrayList<>();

    public static void addPlugin(Class<?> iface, String name, Class<?> impl)
    {
        plugins.add(new PluginDefinition(iface, name, impl));
    }

	@Override
	public List<Module> getModules(ConfigSource configsource) {
		Module module = new Module() {

			@Override
			public void configure(Binder binder) {
                for (PluginDefinition plugin : plugins) {
                    InjectedPluginSource.registerPluginTo(binder, plugin.iface, plugin.name, plugin.impl);
                }
			}
		};
		return ImmutableList.of(module);
	}

}

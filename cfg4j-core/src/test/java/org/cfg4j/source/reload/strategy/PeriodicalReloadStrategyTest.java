/*
 * Copyright 2015-2016 Norbert Potocki (norbert.potocki@nort.pl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cfg4j.source.reload.strategy;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.cfg4j.source.reload.Reloadable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.TimeUnit;


@RunWith(MockitoJUnitRunner.class)
public class PeriodicalReloadStrategyTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Mock
  private Reloadable reloadable;

  @Mock
  private Reloadable reloadable2;

  @Test
  public void shouldReloadImmediatelyAfterRegistered() throws Exception {
    PeriodicalReloadStrategy strategy = new PeriodicalReloadStrategy(60, TimeUnit.SECONDS);

    strategy.register(reloadable);
    strategy.deregister(reloadable);

    verify(reloadable, times(1)).reload();
  }

  @Test
  public void shouldSuppressException() throws Exception {
    doThrow(new IllegalStateException()).when(reloadable).reload();
    PeriodicalReloadStrategy strategy = new PeriodicalReloadStrategy(60, TimeUnit.SECONDS);

    strategy.register(reloadable);

    verify(reloadable, times(1)).reload();
  }
}
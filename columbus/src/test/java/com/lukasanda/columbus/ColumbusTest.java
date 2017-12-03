/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lukasanda.columbus;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 23) // Works around https://github.com/robolectric/robolectric/issues/2566.
public class ColumbusTest {

//  @Mock Context context;
//  @Mock Downloader downloader;
//  @Mock Dispatcher dispatcher;
//  @Mock Columbus.RequestTransformer transformer;
//  @Mock RequestHandler requestHandler;
//  @Mock Cache cache;
//  @Mock
//  Columbus.Listener listener;
//  @Mock Stats stats;
//
//  private Columbus columbus;
//  final Bitmap bitmap = TestUtils.makeBitmap();
//
//  @Before public void setUp() {
//    initMocks(this);
//    columbus = new Columbus(context, dispatcher, cache, listener, transformer, null, stats, ARGB_8888,
//        false, false);
//  }
//
//  @Test public void submitWithNullTargetInvokesDispatcher() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1);
//    columbus.enqueueAndSubmit(action);
//    assertThat(columbus.targetToAction).isEmpty();
//    verify(dispatcher).dispatchSubmit(action);
//  }
//
//  @Test public void submitWithTargetInvokesDispatcher() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    assertThat(columbus.targetToAction).isEmpty();
//    columbus.enqueueAndSubmit(action);
//    assertThat(columbus.targetToAction).hasSize(1);
//    verify(dispatcher).dispatchSubmit(action);
//  }
//
//  @Test public void submitWithSameActionDoesNotCancel() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    columbus.enqueueAndSubmit(action);
//    verify(dispatcher).dispatchSubmit(action);
//    assertThat(columbus.targetToAction).hasSize(1);
//    assertThat(columbus.targetToAction.containsValue(action)).isTrue();
//    columbus.enqueueAndSubmit(action);
//    verify(action, never()).cancel();
//    verify(dispatcher, never()).dispatchCancel(action);
//  }
//
//  @Test public void quickMemoryCheckReturnsBitmapIfInCache() {
//    when(cache.get(TestUtils.URI_KEY_1)).thenReturn(bitmap);
//    Bitmap cached = columbus.quickMemoryCacheCheck(TestUtils.URI_KEY_1);
//    assertThat(cached).isEqualTo(bitmap);
//    verify(stats).dispatchCacheHit();
//  }
//
//  @Test public void quickMemoryCheckReturnsNullIfNotInCache() {
//    Bitmap cached = columbus.quickMemoryCacheCheck(TestUtils.URI_KEY_1);
//    assertThat(cached).isNull();
//    verify(stats).dispatchCacheMiss();
//  }
//
//  @Test public void completeInvokesSuccessOnAllSuccessfulRequests() {
//    Action action1 = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    Action action2 = TestUtils.mockCanceledAction();
//    BitmapHunter hunter = TestUtils.mockHunter(TestUtils.URI_KEY_1, bitmap, false);
//    when(hunter.getActions()).thenReturn(Arrays.asList(action1, action2));
//    when(hunter.getLoadedFrom()).thenReturn(Columbus.LoadedFrom.MEMORY);
//    columbus.complete(hunter);
//    verify(action1).complete(bitmap, Columbus.LoadedFrom.MEMORY);
//    verify(action2, never()).complete(eq(bitmap), any(Columbus.LoadedFrom.class));
//  }
//
//  @Test public void completeInvokesErrorOnAllFailedRequests() {
//    Action action1 = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    Action action2 = TestUtils.mockCanceledAction();
//    Exception exception = mock(Exception.class);
//    BitmapHunter hunter = TestUtils.mockHunter(TestUtils.URI_KEY_1, null, false);
//    when(hunter.getException()).thenReturn(exception);
//    when(hunter.getActions()).thenReturn(Arrays.asList(action1, action2));
//    columbus.complete(hunter);
//    verify(action1).error(exception);
//    verify(action2, never()).error(exception);
//    verify(listener).onImageLoadFailed(columbus, TestUtils.URI_1, exception);
//  }
//
//  @Test public void completeDeliversToSingle() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    BitmapHunter hunter = TestUtils.mockHunter(TestUtils.URI_KEY_1, bitmap, false);
//    when(hunter.getLoadedFrom()).thenReturn(Columbus.LoadedFrom.MEMORY);
//    when(hunter.getAction()).thenReturn(action);
//    when(hunter.getActions()).thenReturn(Collections.<Action>emptyList());
//    columbus.complete(hunter);
//    verify(action).complete(bitmap, Columbus.LoadedFrom.MEMORY);
//  }
//
//  @Test public void completeWithReplayDoesNotRemove() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    when(action.willReplay()).thenReturn(true);
//    BitmapHunter hunter = TestUtils.mockHunter(TestUtils.URI_KEY_1, bitmap, false);
//    when(hunter.getLoadedFrom()).thenReturn(Columbus.LoadedFrom.MEMORY);
//    when(hunter.getAction()).thenReturn(action);
//    columbus.enqueueAndSubmit(action);
//    assertThat(columbus.targetToAction).hasSize(1);
//    columbus.complete(hunter);
//    assertThat(columbus.targetToAction).hasSize(1);
//    verify(action).complete(bitmap, Columbus.LoadedFrom.MEMORY);
//  }
//
//  @Test public void completeDeliversToSingleAndMultiple() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    Action action2 = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    BitmapHunter hunter = TestUtils.mockHunter(TestUtils.URI_KEY_1, bitmap, false);
//    when(hunter.getLoadedFrom()).thenReturn(Columbus.LoadedFrom.MEMORY);
//    when(hunter.getAction()).thenReturn(action);
//    when(hunter.getActions()).thenReturn(Arrays.asList(action2));
//    columbus.complete(hunter);
//    verify(action).complete(bitmap, Columbus.LoadedFrom.MEMORY);
//    verify(action2).complete(bitmap, Columbus.LoadedFrom.MEMORY);
//  }
//
//  @Test public void completeSkipsIfNoActions() {
//    BitmapHunter hunter = TestUtils.mockHunter(TestUtils.URI_KEY_1, bitmap, false);
//    columbus.complete(hunter);
//    verify(hunter).getAction();
//    verify(hunter).getActions();
//    verifyNoMoreInteractions(hunter);
//  }
//
//  @Test public void loadedFromIsNullThrows() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, TestUtils.mockImageViewTarget());
//    BitmapHunter hunter = TestUtils.mockHunter(TestUtils.URI_KEY_1, bitmap, false);
//    when(hunter.getAction()).thenReturn(action);
//    try {
//      columbus.complete(hunter);
//      fail("Calling complete() with null LoadedFrom should throw");
//    } catch (AssertionError expected) {
//    }
//  }
//
//  @Test public void resumeActionTriggersSubmitOnPausedAction() {
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1);
//    columbus.resumeAction(action);
//    verify(dispatcher).dispatchSubmit(action);
//  }
//
//  @Test public void resumeActionImmediatelyCompletesCachedRequest() {
//    when(cache.get(TestUtils.URI_KEY_1)).thenReturn(bitmap);
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1);
//    columbus.resumeAction(action);
//    verify(action).complete(bitmap, Columbus.LoadedFrom.MEMORY);
//  }
//
//  @Test public void cancelExistingRequestWithUnknownTarget() {
//    ImageView target = TestUtils.mockImageViewTarget();
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, target);
//    columbus.cancelRequest(target);
//    verifyZeroInteractions(action, dispatcher);
//  }
//
//  @Test public void cancelExistingRequestWithNullImageView() {
//    try {
//      columbus.cancelRequest((ImageView) null);
//      fail("Canceling with a null ImageView should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//  }
//
//  @Test public void cancelExistingRequestWithNullTarget() {
//    try {
//      columbus.cancelRequest((Target) null);
//      fail("Canceling with a null target should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//  }
//
//  @Test public void cancelExistingRequestWithImageViewTarget() {
//    ImageView target = TestUtils.mockImageViewTarget();
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, target);
//    columbus.enqueueAndSubmit(action);
//    assertThat(columbus.targetToAction).hasSize(1);
//    columbus.cancelRequest(target);
//    assertThat(columbus.targetToAction).isEmpty();
//    verify(action).cancel();
//    verify(dispatcher).dispatchCancel(action);
//  }
//
//  @Test public void cancelExistingRequestWithDeferredImageViewTarget() {
//    ImageView target = TestUtils.mockImageViewTarget();
//    DeferredRequestCreator deferredRequestCreator = TestUtils.mockDeferredRequestCreator();
//    columbus.targetToDeferredRequestCreator.put(target, deferredRequestCreator);
//    columbus.cancelRequest(target);
//    verify(deferredRequestCreator).cancel();
//    assertThat(columbus.targetToDeferredRequestCreator).isEmpty();
//  }
//
//  @Test public void enqueueingDeferredRequestCancelsThePreviousOne() throws Exception {
//    ImageView target = TestUtils.mockImageViewTarget();
//    DeferredRequestCreator firstRequestCreator = TestUtils.mockDeferredRequestCreator();
//    columbus.defer(target, firstRequestCreator);
//    assertThat(columbus.targetToDeferredRequestCreator).containsKey(target);
//
//    DeferredRequestCreator secondRequestCreator = TestUtils.mockDeferredRequestCreator();
//    columbus.defer(target, secondRequestCreator);
//    verify(firstRequestCreator).cancel();
//    assertThat(columbus.targetToDeferredRequestCreator).containsKey(target);
//  }
//
//  @Test public void cancelExistingRequestWithTarget() {
//    Target target = TestUtils.mockTarget();
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, target);
//    columbus.enqueueAndSubmit(action);
//    assertThat(columbus.targetToAction).hasSize(1);
//    columbus.cancelRequest(target);
//    assertThat(columbus.targetToAction).isEmpty();
//    verify(action).cancel();
//    verify(dispatcher).dispatchCancel(action);
//  }
//
//  @Test public void cancelExistingRequestWithNullRemoteViews() {
//    try {
//      columbus.cancelRequest(null, 0);
//      fail("Canceling with a null RemoteViews should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//  }
//
//  @Config(sdk = 16) // This test fails on 23 so restore the default level.
//  @Test public void cancelExistingRequestWithRemoteViewTarget() {
//    int layoutId = 0;
//    int viewId = 1;
//    RemoteViews remoteViews = new RemoteViews("packageName", layoutId);
//    RemoteViewsAction.RemoteViewsTarget target = new RemoteViewsAction.RemoteViewsTarget(remoteViews, viewId);
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, target);
//    columbus.enqueueAndSubmit(action);
//    assertThat(columbus.targetToAction).hasSize(1);
//    columbus.cancelRequest(remoteViews, viewId);
//    assertThat(columbus.targetToAction).isEmpty();
//    verify(action).cancel();
//    verify(dispatcher).dispatchCancel(action);
//  }
//
//  @Test public void cancelNullTagThrows() {
//    try {
//      columbus.cancelTag(null);
//      fail("Canceling with a null tag should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//  }
//
//  @Test public void cancelTagAllActions() {
//    ImageView target = TestUtils.mockImageViewTarget();
//    Action action = TestUtils.mockAction(TestUtils.URI_KEY_1, TestUtils.URI_1, target, "TAG");
//    columbus.enqueueAndSubmit(action);
//    assertThat(columbus.targetToAction).hasSize(1);
//    columbus.cancelTag("TAG");
//    assertThat(columbus.targetToAction).isEmpty();
//    verify(action).cancel();
//  }
//
//  @Test public void cancelTagAllDeferredRequests() {
//    ImageView target = TestUtils.mockImageViewTarget();
//    DeferredRequestCreator deferredRequestCreator = TestUtils.mockDeferredRequestCreator();
//    when(deferredRequestCreator.getTag()).thenReturn("TAG");
//    columbus.defer(target, deferredRequestCreator);
//    columbus.cancelTag("TAG");
//    verify(deferredRequestCreator).cancel();
//  }
//
//  @Test public void deferAddsToMap() {
//    ImageView target = TestUtils.mockImageViewTarget();
//    DeferredRequestCreator deferredRequestCreator = TestUtils.mockDeferredRequestCreator();
//    assertThat(columbus.targetToDeferredRequestCreator).isEmpty();
//    columbus.defer(target, deferredRequestCreator);
//    assertThat(columbus.targetToDeferredRequestCreator).hasSize(1);
//  }
//
//  @Test public void shutdown() {
//    columbus.shutdown();
//    verify(cache).clear();
//    verify(stats).shutdown();
//    verify(dispatcher).shutdown();
//    assertThat(columbus.shutdown).isTrue();
//  }
//
//  @Test public void shutdownTwice() {
//    columbus.shutdown();
//    columbus.shutdown();
//    verify(cache).clear();
//    verify(stats).shutdown();
//    verify(dispatcher).shutdown();
//    assertThat(columbus.shutdown).isTrue();
//  }
//
//  @Test public void shutdownDisallowedOnSingletonInstance() {
//    columbus.singleton = null;
//    ColumbusProvider.context = RuntimeEnvironment.application;
//    try {
//      columbus.with().shutdown();
//      fail("Calling shutdown() on static singleton instance should throw");
//    } catch (UnsupportedOperationException expected) {
//    }
//  }
//
//  @Test public void shutdownDisallowedOnCustomSingletonInstance() {
//    columbus.singleton = null;
//    try {
//      Columbus columbus = new Columbus.Builder(RuntimeEnvironment.application).build();
//      columbus.setSingletonInstance(columbus);
//      columbus.shutdown();
//      fail("Calling shutdown() on static singleton instance should throw");
//    } catch (UnsupportedOperationException expected) {
//    }
//  }
//
//  @Test public void setSingletonInstanceRejectsNull() {
//    columbus.singleton = null;
//
//    try {
//      columbus.setSingletonInstance(null);
//      fail("Can't set singleton instance to null.");
//    } catch (IllegalArgumentException e) {
//      assertThat(e).hasMessage("Columbus must not be null.");
//    }
//  }
//
//  @Test public void setSingletonInstanceMayOnlyBeCalledOnce() {
//    columbus.singleton = null;
//
//    Columbus columbus = new Columbus.Builder(RuntimeEnvironment.application).build();
//    columbus.setSingletonInstance(columbus);
//
//    try {
//      columbus.setSingletonInstance(columbus);
//      fail("Can't set singleton instance twice.");
//    } catch (IllegalStateException e) {
//      assertThat(e).hasMessage("Singleton instance already exists.");
//    }
//  }
//
//  @Test public void setSingletonInstanceAfterWithFails() {
//    columbus.singleton = null;
//    ColumbusProvider.context = RuntimeEnvironment.application;
//
//    // Implicitly create the default singleton instance.
//    columbus.with();
//
//    Columbus columbus = new Columbus.Builder(RuntimeEnvironment.application).build();
//    try {
//      columbus.setSingletonInstance(columbus);
//      fail("Can't set singleton instance after with().");
//    } catch (IllegalStateException e) {
//      assertThat(e).hasMessage("Singleton instance already exists.");
//    }
//  }
//
//  @Test public void setSingleInstanceReturnedFromWith() {
//    columbus.singleton = null;
//    Columbus columbus = new Columbus.Builder(RuntimeEnvironment.application).build();
//    columbus.setSingletonInstance(columbus);
//    assertThat(columbus.with()).isSameAs(columbus);
//  }
//
//  @Test public void shutdownClearsDeferredRequests() {
//    DeferredRequestCreator deferredRequestCreator = TestUtils.mockDeferredRequestCreator();
//    ImageView target = TestUtils.mockImageViewTarget();
//    columbus.targetToDeferredRequestCreator.put(target, deferredRequestCreator);
//    columbus.shutdown();
//    verify(deferredRequestCreator).cancel();
//    assertThat(columbus.targetToDeferredRequestCreator).isEmpty();
//  }
//
//  @Test public void whenTransformRequestReturnsNullThrows() {
//    try {
//      when(transformer.transformRequest(any(Request.class))).thenReturn(null);
//      columbus.transformRequest(new Request.Builder(TestUtils.URI_1).build());
//      fail("Returning null from transformRequest() should throw");
//    } catch (IllegalStateException expected) {
//    }
//  }
//
//  @Test public void getSnapshotInvokesStats() {
//    columbus.getSnapshot();
//    verify(stats).createSnapshot();
//  }
//
//  @Test public void enableIndicators() {
//    assertThat(columbus.areIndicatorsEnabled()).isFalse();
//    columbus.setIndicatorsEnabled(true);
//    assertThat(columbus.areIndicatorsEnabled()).isTrue();
//  }
//
//  @Test public void loadThrowsWithInvalidInput() {
//    try {
//      columbus.load("");
//      fail("Empty URL should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      columbus.load("      ");
//      fail("Empty URL should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      columbus.load(0);
//      fail("Zero resourceId should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//  }
//
//  @Test public void builderInvalidListener() {
//    try {
//      new Columbus.Builder(context).listener(null);
//      fail("Null listener should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      new Columbus.Builder(context).listener(listener).listener(listener);
//      fail("Setting Listener twice should throw exception.");
//    } catch (IllegalStateException expected) {
//    }
//  }
//
//  @Test public void builderInvalidLoader() {
//    try {
//      new Columbus.Builder(context).downloader(null);
//      fail("Null Downloader should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      new Columbus.Builder(context).downloader(downloader).downloader(downloader);
//      fail("Setting Downloader twice should throw exception.");
//    } catch (IllegalStateException expected) {
//    }
//  }
//
//  @Test public void builderInvalidExecutor() {
//    try {
//      new Columbus.Builder(context).executor(null);
//      fail("Null Executor should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      ExecutorService executor = mock(ExecutorService.class);
//      new Columbus.Builder(context).executor(executor).executor(executor);
//      fail("Setting Executor twice should throw exception.");
//    } catch (IllegalStateException expected) {
//    }
//  }
//
//  @Test public void builderInvalidCache() {
//    try {
//      new Columbus.Builder(context).memoryCache(null);
//      fail("Null Cache should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      new Columbus.Builder(context).memoryCache(cache).memoryCache(cache);
//      fail("Setting Cache twice should throw exception.");
//    } catch (IllegalStateException expected) {
//    }
//  }
//
//  @Test public void builderInvalidRequestTransformer() {
//    try {
//      new Columbus.Builder(context).requestTransformer(null);
//      fail("Null request transformer should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      new Columbus.Builder(context).requestTransformer(transformer).requestTransformer(transformer);
//      fail("Setting request transformer twice should throw exception.");
//    } catch (IllegalStateException expected) {
//    }
//  }
//
//  @Test public void builderInvalidRequestHandler() {
//    try {
//      new Columbus.Builder(context).addRequestHandler(null);
//      fail("Null request handler should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//    try {
//      new Columbus.Builder(context).addRequestHandler(requestHandler)
//          .addRequestHandler(requestHandler);
//      fail("Registering same request handler twice should throw exception.");
//    } catch (IllegalStateException expected) {
//    }
//  }
//
//  @Test public void builderWithoutRequestHandler() {
//    Columbus columbus = new Columbus.Builder(RuntimeEnvironment.application).build();
//    assertThat(columbus.getRequestHandlers()).isNotEmpty();
//    assertThat(columbus.getRequestHandlers()).doesNotContain(requestHandler);
//  }
//
//  @Test public void builderWithRequestHandler() {
//    Columbus columbus = new Columbus.Builder(RuntimeEnvironment.application)
//        .addRequestHandler(requestHandler).build();
//    assertThat(columbus.getRequestHandlers()).isNotNull();
//    assertThat(columbus.getRequestHandlers()).isNotEmpty();
//    assertThat(columbus.getRequestHandlers()).contains(requestHandler);
//  }
//
//  @Test public void builderInvalidContext() {
//    try {
//      new Columbus.Builder(null);
//      fail("Null context should throw exception.");
//    } catch (IllegalArgumentException expected) {
//    }
//  }
//
//  @Test public void builderWithDebugIndicators() {
//    Columbus columbus = new Columbus.Builder(RuntimeEnvironment.application).indicatorsEnabled(true).build();
//    assertThat(columbus.areIndicatorsEnabled()).isTrue();
//  }
//
//  @Test public void invalidateString() {
//    columbus.invalidate("http://example.com");
//    verify(cache).clearKeyUri("http://example.com");
//  }
//
//  @Test public void invalidateFile() {
//    columbus.invalidate(new File("/foo/bar/baz"));
//    verify(cache).clearKeyUri("file:///foo/bar/baz");
//  }
//
//  @Test public void invalidateUri() {
//    columbus.invalidate(Uri.parse("mock://12345"));
//    verify(cache).clearKeyUri("mock://12345");
//  }
}
